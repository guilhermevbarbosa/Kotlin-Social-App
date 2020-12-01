package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/2a78f1da5be841d9946f661cde09ad9f/categorias")
    fun list(): Call<List<Category>>
}