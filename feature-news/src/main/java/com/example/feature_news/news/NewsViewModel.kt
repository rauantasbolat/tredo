package com.example.feature_news.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.core.database.Article
import com.example.core.response.ArticleResponse
import com.example.feature_news.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<ArticleResponse>>()
    val news: LiveData<List<ArticleResponse>> get() = _news

    private val _currentQuery = MutableLiveData<Pair<String, String>>()
    private val currentQuery: LiveData<Pair<String, String>> = _currentQuery

    val newsFlow: LiveData<PagingData<ArticleResponse>> = currentQuery.switchMap { query ->
        newsRepository.getNewsStream(query.first, query.second)
            .flow
            .map { pagingData ->
                pagingData.filter { article ->
                    article.urlToImage != null
                }
            }
            .cachedIn(viewModelScope)
            .asLiveData()
    }

    fun searchNews(query: String, sortBy: String? = null) {
        _currentQuery.value = Pair(query, sortBy ?: "relevance")
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newsRepository.saveNews(article)

            }
        }
    }
}