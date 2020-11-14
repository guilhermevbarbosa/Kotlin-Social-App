package com.example.tsisocialapp.services

import com.example.tsisocialapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("/api/7a68d84a996e4b51825f0cc0de6b5750/category")
    fun list(): Call<List<Category>>
}