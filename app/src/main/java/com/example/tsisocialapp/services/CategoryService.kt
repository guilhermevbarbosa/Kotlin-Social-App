package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/9294833f4be54b7ca0a2c01db6e730cf/categorias")
    fun list(): Call<List<Category>>
}