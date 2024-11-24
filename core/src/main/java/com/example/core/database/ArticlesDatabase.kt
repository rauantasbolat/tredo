package com.example.core.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.response.ArticleResponse

@Database(entities = [Article::class], version = 1)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticlesDao
}