package com.example.core.response


data class NewsResponse(
    val articles: List<ArticleResponse>,
    val status: String,
    val totalResults: Int
)

data class ArticleResponse(
    val url: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val urlToImage: String,
)
