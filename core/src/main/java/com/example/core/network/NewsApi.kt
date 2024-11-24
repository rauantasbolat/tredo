package com.example.core.network

import com.example.core.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String?,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String? = "ru"
    ): Response<NewsResponse>
}