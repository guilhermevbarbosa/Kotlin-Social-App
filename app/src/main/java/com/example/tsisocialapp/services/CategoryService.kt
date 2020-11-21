package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/260270692e294a38b21e52d0fce3dd76/categorias")
    fun list(): Call<List<Category>>
}