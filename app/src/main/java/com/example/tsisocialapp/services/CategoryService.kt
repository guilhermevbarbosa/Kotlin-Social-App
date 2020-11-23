package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/24a2f9a8a9eb4645b64768fb822e742d/categorias")
    fun list(): Call<List<Category>>
}