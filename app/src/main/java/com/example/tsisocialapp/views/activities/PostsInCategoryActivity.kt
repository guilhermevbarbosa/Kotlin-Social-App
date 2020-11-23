package com.example.tsisocialapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Post
import com.example.tsisocialapp.utils.convertSnapshotToPostList
import com.example.tsisocialapp.utils.getCurrentUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_posts_in_category.*
import kotlinx.android.synthetic.main.options_card.view.*

class PostsInCategoryActivity : AppCompatActivity() {
    var database: DatabaseReference? = null
    var categoriaSelecionada: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_in_category)
        configurarFirebase()

        categoriaSelecionada = intent.getStringExtra("categoria")
    }

    fun configurarFirebase(){
        database = FirebaseDatabase.getInstance().reference.child("posts")

        val vEvListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                refreshUI(convertSnapshotToPostList(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PostsInCategoryActivity, "Erro de servidor", Toast.LENGTH_LONG).show()
                Log.e("PostListActivity", "configurarFirebase", error.toException())
            }
        }

        database?.addValueEventListener(vEvListener)
    }

    fun refreshUI(posts: List<Post>){
        containerAP.removeAllViews()

        posts.forEach {
            val cardPost = layoutInflater.inflate(R.layout.options_card, containerAP, false)

            if (it.category == categoriaSelecionada){
                cardPost.txtBtn.text = it.title
                containerAP.addView(cardPost)
            }
        }
    }
}