package com.example.tsisocialapp.views.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.tsisocialapp.MainActivity
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Category
import com.example.tsisocialapp.model.Post
import com.example.tsisocialapp.services.CategoryService
import com.example.tsisocialapp.utils.getCurrentUser
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class PostFragment : Fragment() {
    var database: DatabaseReference? = null
    var storage: StorageReference? = null

    var img: Uri? = null

    //image pick code
    private val IMAGE_PICK_CODE = 1000;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        configurarFirebase()
        configurarFirebaseStorage()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getCategories()

        selectImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        btnSave.setOnClickListener {
            if(img != null){
                uploadImage()
            }else{
                addPost()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            image_view.setImageURI(data?.data)
            img = data?.data
        }
    }

    fun getCategories(){
        val retrofit = Retrofit.Builder().baseUrl("https://crudcrud.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CategoryService::class.java)
        val call = service.list()

        val callback = object: Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()

                    val itensSpinner = arrayListOf<String>()
                    categories?.forEach {
                        itensSpinner.add(it.name)
                    }

                    val adapter = ArrayAdapter(
                        activity!!,
                        android.R.layout.simple_spinner_item,
                        itensSpinner
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFrag.adapter = adapter
                }
                else {
                    Toast.makeText(context, "Não há categorias", Toast.LENGTH_LONG).show()
                    Log.e("ERRO", "Erro da API")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(context, "Não foi possível recuperar categorias", Toast.LENGTH_LONG).show()
                Log.e("ERRO", "Falha ao chamar o serviço", t)
            }
        }

        call.enqueue(callback)
    }

    fun configurarFirebase(){
        database = FirebaseDatabase.getInstance().reference.child("posts")
    }

    fun configurarFirebaseStorage(){
        storage = FirebaseStorage.getInstance().reference.child("posts")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImage(){
        val progressBar = ProgressDialog(context)
        progressBar.setMessage("Fazendo upload da imagem")
        progressBar.show()

        if (img != null){
            val storageRef = storage!!.child(System.currentTimeMillis().toString())

            var uploadTask: StorageTask<*>
            uploadTask = storageRef.putFile(img!!)

            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation storageRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()
                    progressBar.dismiss()

                    addPost(url)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPost(imgUrl: String? = ""){
        val uid = getCurrentUser()?.uid
        val currentTime = LocalDateTime.now()

        val post = Post(
            user_uid = uid!!,
            timestamp = currentTime.toString(),
            title = etTitulo.text.toString(),
            text = etTexto.text.toString(),
            category = spinnerFrag.selectedItem as String,
            likes = 0,
            image = imgUrl
        )

        val novaEntrada = database?.push()

        post.id = novaEntrada?.key
        novaEntrada?.setValue(post)

        activity?.let{
            val intent = Intent(it, MainActivity::class.java)
            it.startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostFragment()
    }
}