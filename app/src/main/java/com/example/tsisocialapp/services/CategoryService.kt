package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/1bdfa298b1d74cb8ba13c2730bc997d7/categorias")
    fun list(): Call<List<Category>>
}