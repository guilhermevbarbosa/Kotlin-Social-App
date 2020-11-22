package com.example.tsisocialapp.model

data class Post (
    var id: String? = null,
    var user_uid: String,
    var user: String,
    var timestamp: String,
    var title: String,
    var text: String,
    var category: String,
    var likes: Long,
    var image: String? = null
)