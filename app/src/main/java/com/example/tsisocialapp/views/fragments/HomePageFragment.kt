package com.example.tsisocialapp.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Category
import com.example.tsisocialapp.services.CategoryService
import com.example.tsisocialapp.views.activities.MyPostsActivity
import com.example.tsisocialapp.views.activities.PostsInCategoryActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.options_card.view.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.*

class HomePageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        containerHome.removeAllViews()

        getCategories()
    }

    fun getCategories(){
        val retrofit = Retrofit.Builder().baseUrl("https://crudcrud.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CategoryService::class.java)
        val call = service.list()

        val callback = object: Callback<List<Category>>{
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    refreshUi(categories)
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

    fun refreshUi(categories: List<Category>?){
        categories?.let {
            for (category in it){
                val card = layoutInflater.inflate(R.layout.options_card, containerHome, false)

                card.txtBtn.text = category.name

                card.setOnClickListener {
                    activity?.let{
                        val intent = Intent(it, PostsInCategoryActivity::class.java)
                        it.startActivity(intent)
                    }
                }

                containerHome.addView(card)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomePageFragment()
    }
}