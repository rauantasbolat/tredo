package com.example.feature_news.savedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.database.Article
import com.example.core.response.ArticleResponse
import com.example.core.toArticleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val repository: SavedNewsRepository
) : ViewModel() {

    val savedArticles: StateFlow<List<Article>> = repository.getSavedArticles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteNews(article)
        }
    }
}
