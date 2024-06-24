package com.example.project2.data

import kotlinx.coroutines.flow.Flow


interface ArticleRepository {

    fun getAllItemsStream(): Flow<List<ArticleItem>>

    fun getAllItemsAsc(): Flow<List<ArticleItem>>

    fun getAllItemsDesc(): Flow<List<ArticleItem>>

    fun getItemStream(id: Int): Flow<ArticleItem?>

    fun getPrice(): Flow<Double>

    suspend fun clearAllArticles()

    suspend fun insertItem(item: ArticleItem)

    suspend fun deleteItem(item: ArticleItem)

}