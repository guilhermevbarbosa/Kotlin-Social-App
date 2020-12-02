package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/14f37027f2904f74b3ebe94a2f71ae60/categorias")
    fun list(): Call<List<Category>>
}