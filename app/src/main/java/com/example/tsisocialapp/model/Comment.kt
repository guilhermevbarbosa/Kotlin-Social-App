package com.example.tsisocialapp.model

data class Comment (
    var id: String? = null,
    var user_uid: String,
    var email: String,
    var comment: String,
    var timestamp: String,
)