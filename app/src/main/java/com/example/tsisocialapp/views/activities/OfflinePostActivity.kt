package com.example.tsisocialapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.tsisocialapp.R
import com.example.tsisocialapp.db.AppDatabase
import com.example.tsisocialapp.model.PostRoom
import kotlinx.android.synthetic.main.activity_offline_post.*

class OfflinePostActivity : AppCompatActivity() {
    var postSelecionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_post)
    }

    override fun onResume() {
        super.onResume()
        postSelecionado = intent.getStringExtra("post")

        atualizarPostsBD()
    }

    fun atualizarPostsBD(){
        Thread{
            val db = Room.databaseBuilder(this, AppDatabase::class.java, "UserDb").build()
            val posts = db.PostRoomDao().getOne(postSelecionado!!)

            // alteracoes de interface
            runOnUiThread {
                atualizaPostsView(posts)
            }
        }.start()
    }

    fun atualizaPostsView(post: PostRoom){
        tvOffTitulo.text = post.title
        tvOffTexto.text = post.text
        tvOffCategoria.text = post.category
        tvOffTimestamp.text = post.timestamp
        tvOffUserData.text = post.user
    }
}