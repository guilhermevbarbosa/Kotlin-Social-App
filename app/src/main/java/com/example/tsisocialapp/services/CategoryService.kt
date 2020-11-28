package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/44ba4368807e416995c24c85614aa078/categorias")
    fun list(): Call<List<Category>>
}