package com.example.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(news: Article)

    @Query("SELECT * FROM article_table")
    fun getSavedNews(): Flow<List<Article>>

    @Delete
    fun deleteNews(news: Article)
}