package com.example.tsisocialapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Post
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_post_list.*
import kotlinx.android.synthetic.main.options_card.*
import kotlinx.android.synthetic.main.options_card.view.*

class PostListActivity : AppCompatActivity() {
    var database: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        configurarFirebase()
    }

    fun configurarFirebase(){
        database = FirebaseDatabase.getInstance().reference.child("posts")

        val vEvListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                refreshUI(convertSnapshotToPostList(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PostListActivity, "Erro de servidor", Toast.LENGTH_LONG).show()
                Log.e("PostListActivity", "configurarFirebase", error.toException())
            }
        }

        database?.addValueEventListener(vEvListener)
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

    fun refreshUI(posts: List<Post>){
        container.removeAllViews()

        posts.forEach {
            val cardPost = layoutInflater.inflate(R.layout.options_card, container, false)

            cardPost.txtBtn.text = it.title
            container.addView(cardPost)
        }
    }
}