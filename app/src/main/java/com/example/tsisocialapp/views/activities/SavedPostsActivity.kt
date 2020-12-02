package com.example.tsisocialapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.tsisocialapp.R
import com.example.tsisocialapp.db.AppDatabase
import com.example.tsisocialapp.model.PostRoom
import kotlinx.android.synthetic.main.activity_post_list.*
import kotlinx.android.synthetic.main.activity_post_list.container
import kotlinx.android.synthetic.main.activity_saved_posts.*
import kotlinx.android.synthetic.main.options_card.view.*
import kotlinx.android.synthetic.main.options_card.view.txtBtn
import kotlinx.android.synthetic.main.options_card_post2.view.*

class SavedPostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_posts)
    }

    override fun onResume() {
        super.onResume()
        atualizarPostsBD()
    }

    fun atualizarPostsBD(){
        Thread{
            val db = Room.databaseBuilder(this, AppDatabase::class.java, "UserDb").build()

            val posts = db.PostRoomDao().listarTodas()

            // alteracoes de interface
            runOnUiThread {
                atualizaPostsView(posts)
            }
        }.start()
    }

    fun deletarPostBD(postId: String){
        Thread{
            val db = Room.databaseBuilder(this, AppDatabase::class.java, "UserDb").build()

            db.PostRoomDao().deleteOne(postId)
        }.start()
    }

    fun atualizaPostsView(posts: List<PostRoom>){
        savedContainer.removeAllViews()

        posts.forEach {
            val cardPost = layoutInflater.inflate(R.layout.options_card_post2, savedContainer, false)

            cardPost.txtBtn.text = it.title

            cardPost.deleteBtn.setOnClickListener {post ->
                deletarPostBD(it.id)
                atualizarPostsBD()

                Toast.makeText(this@SavedPostsActivity, "Excluido com sucesso", Toast.LENGTH_SHORT).show()
            }

            cardPost.setOnClickListener { post->
                val intent = Intent(this, OfflinePostActivity::class.java)
                intent.putExtra("post", it.id)

                startActivity(intent)
            }

            savedContainer.addView(cardPost)
        }
    }
}