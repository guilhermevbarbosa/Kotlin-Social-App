package com.example.tsisocialapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tsisocialapp.model.PostRoom

@Dao
interface PostRoomDao {

    @Insert
    fun inserir(post: PostRoom)

    @Query(value = "select * from PostRoom")
    fun listarTodas(): List<PostRoom>
}