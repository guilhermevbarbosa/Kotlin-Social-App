package com.example.tsisocialapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostRoom (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var user: String,
    var timestamp: String,
    var title: String,
    var text: String,
    var category: String,
)