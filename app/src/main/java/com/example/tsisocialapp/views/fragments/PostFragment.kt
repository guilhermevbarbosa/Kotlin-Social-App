package com.example.tsisocialapp.views.fragments

import android.R.attr.data
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Category
import com.example.tsisocialapp.services.CategoryService
import kotlinx.android.synthetic.main.fragment_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        getCategories()
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

    companion object {
        @JvmStatic
        fun newInstance() = PostFragment()
    }
}