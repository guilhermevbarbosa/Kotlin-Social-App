package com.example.tsisocialapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tsisocialapp.model.PostRoom

@Database(entities = arrayOf(PostRoom::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun PostRoomDao(): PostRoomDao
}