package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.database.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ArticlesDatabase {
        return Room.databaseBuilder(context, ArticlesDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideArticleDao(database: ArticlesDatabase) = database.articleDao()
}