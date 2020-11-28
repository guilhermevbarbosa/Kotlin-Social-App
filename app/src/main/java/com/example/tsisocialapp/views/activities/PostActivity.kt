package com.example.tsisocialapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tsisocialapp.R
import com.example.tsisocialapp.model.Post
import com.example.tsisocialapp.utils.convertSnapshotToPost
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    var database: DatabaseReference? = null
    var postSelecionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        postSelecionado = intent.getStringExtra("post")

        configurarFirebase()
    }

    private fun configurarFirebase(){
        database = FirebaseDatabase.getInstance().reference.child("posts").child(postSelecionado.toString())

        val vEvListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                convertSnapshotToPost(snapshot)
                refreshUI(convertSnapshotToPost(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PostActivity, "Erro de servidor", Toast.LENGTH_LONG).show()
                Log.e("PostListActivity", "configurarFirebase", error.toException())
            }
        }

        database?.addValueEventListener(vEvListener)
    }

    fun refreshUI(post: Post){
        tvTitulo.text = post.title
        tvTexto.text = post.text
        tvUserData.text = post.user
        tvTimestamp.text = post.timestamp
        tvLikes.text = post.likes.toString()

        if (post.image!!.isNotEmpty()){
            Picasso
                .get()
                .load(post.image)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(ivImage)
        }

        likeBtn.setOnClickListener {
            database?.child("likes")?.setValue(++post.likes)
        }
    }
}