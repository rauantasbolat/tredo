package com.example.feature_news.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.network.NewsApi
import com.example.core.response.ArticleResponse

class NewsPaging(
    private val apiService: NewsApi,
    private val query: String,
    private val sortBy: String?
) : PagingSource<Int, ArticleResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getNews(
                query = query,
                sortBy = sortBy,
                apiKey = "2d161098797c4056bc0709e9d658e6ee"
            )
            val articles = response.body()?.articles ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (articles.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
