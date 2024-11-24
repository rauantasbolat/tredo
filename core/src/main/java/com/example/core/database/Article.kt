package com.example.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var url: String,
    var author: String,
    var content: String,
    var description: String,
    var publishedAt: String,
    var title: String,
    var urlToImage: String
)