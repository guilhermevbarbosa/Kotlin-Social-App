package com.example.tsisocialapp.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import com.example.tsisocialapp.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot

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

fun convertSnapshotToPostList(snapshot: DataSnapshot): List<Post>{
    val postsList = arrayListOf<Post>()

    snapshot.children.forEach {
        val mapa = it.getValue() as HashMap<String, Any>

        val id = mapa.get("id") as String
        val user_uid = mapa.get("user_uid") as String
        val user = mapa.get("user") as String
        val timestamp = mapa.get("timestamp") as String
        val title = mapa.get("title") as String
        val text = mapa.get("text") as String
        val category = mapa.get("category") as String
        val likes = mapa.get("likes") as Long
        val image = mapa.get("image") as String

        val post = Post(id, user_uid, user, timestamp, title, text, category, likes, image)
        postsList.add(post)
    }

    return postsList
}

fun convertSnapshotToPost(snapshot: DataSnapshot){
    snapshot.children.forEach {
        Log.e("teste", it.toString())
    }
}