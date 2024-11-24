package com.example.feature_news.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.core.network.NewsApi
import com.example.core.database.Article
import com.example.core.database.ArticlesDao
import com.example.core.response.ArticleResponse
import com.example.core.response.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao
){
    fun getNewsStream(query: String, sortBy: String?): Pager<Int, ArticleResponse> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPaging(newsApi, query, sortBy = sortBy) }
        )
    }


    suspend fun saveNews(article: Article) = articlesDao.saveNews(article)


}