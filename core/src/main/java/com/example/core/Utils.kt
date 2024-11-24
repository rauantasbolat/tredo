package com.example.core

import androidx.lifecycle.LiveData
import com.example.core.database.Article
import com.example.core.response.ArticleResponse

fun ArticleResponse.toArticle(): Article {
    return Article(
        id = 0,
        url = this.url,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        urlToImage = this.urlToImage
    )
}

fun Article.toArticleResponse(): ArticleResponse {
    return ArticleResponse(
        url = this.url,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        urlToImage = this.urlToImage
    )
}


