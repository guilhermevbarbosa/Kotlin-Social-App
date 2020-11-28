package com.example.tsisocialapp.views.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Comment
import com.example.tsisocialapp.model.Post
import com.example.tsisocialapp.utils.convertSnapshotToPost
import com.example.tsisocialapp.utils.getCurrentUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post.*
import java.time.LocalDateTime

class PostActivity : AppCompatActivity() {
    var database: DatabaseReference? = null
    var postSelecionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        postSelecionado = intent.getStringExtra("post")

        configurarFirebase()
    }

    private fun configurarFirebase(){
        database = FirebaseDatabase.getInstance().reference.child("posts").child(postSelecionado.toString())

        val vEvListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                convertSnapshotToPost(snapshot)
                refreshUI(convertSnapshotToPost(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PostActivity, "Erro de servidor", Toast.LENGTH_LONG).show()
                Log.e("PostListActivity", "configurarFirebase", error.toException())
            }
        }

        database?.addValueEventListener(vEvListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshUI(post: Post){
        tvTitulo.text = post.title
        tvTexto.text = post.text
        tvUserData.text = post.user
        tvTimestamp.text = post.timestamp
        tvLikes.text = post.likes.toString()

        if (post.image!!.isNotEmpty()){
            Picasso
                .get()
                .load(post.image)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(ivImage)
        }

        likeBtn.setOnClickListener {
            database?.child("likes")?.setValue(++post.likes)
        }

        btnComment.setOnClickListener {
            novoComentario()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun novoComentario(){
        val campoTexto = EditText(this)
        campoTexto.hint = "Comentário"

        val uid = getCurrentUser()?.uid
        val email = getCurrentUser()?.email
        val currentTime = LocalDateTime.now()

        AlertDialog.Builder(this)
            .setTitle("Comentário")
            .setView(campoTexto)
            .setPositiveButton("Inserir"){ dialog, button ->
                val comentario = Comment(user_uid = uid.toString(), email = email.toString(), comment = campoTexto.text.toString(), timestamp = currentTime.toString())

                val novaEntrada = database?.child("comentarios")?.push()
                comentario.id = novaEntrada?.key

                novaEntrada?.setValue(comentario)
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }
}