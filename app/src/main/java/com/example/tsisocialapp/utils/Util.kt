package com.example.tsisocialapp.utils

import android.app.AlertDialog
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

    fun getCurrentUser(): FirebaseUser? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }

    fun alert (title: String, msg: String, Context: Context) {
        val builder = AlertDialog.Builder(Context)

        builder
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("OK",null)
            .create()
            .show()
    }