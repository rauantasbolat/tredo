package com.example.feature_news.savedNews

//import com.example.core.database.Article
import com.example.core.database.Article
import com.example.core.database.ArticlesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SavedNewsRepository @Inject constructor(
    private val articlesDao: ArticlesDao
) {
    fun getSavedArticles() : Flow<List<Article>> = articlesDao.getSavedNews()
    fun deleteNews(article: Article) = articlesDao.deleteNews(article)
}