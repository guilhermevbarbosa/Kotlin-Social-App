package com.example.tsisocialapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

    fun getCurrentUser(): FirebaseUser? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }