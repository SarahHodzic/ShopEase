package com.example.project2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ArticleItem)

    @Delete
    suspend fun delete(item: ArticleItem)

    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()

    @Query("SELECT * from articles WHERE id = :id")
    fun getItem(id: Int): Flow<ArticleItem>

    @Query("SELECT * from articles ORDER BY price ASC")
    fun getAllItemsAsc(): Flow<List<ArticleItem>>

    @Query("SELECT * from articles ORDER BY price DESC")
    fun getAllItemsDesc(): Flow<List<ArticleItem>>

    @Query("SELECT SUM(price) from articles")
    fun getPrice(): Flow<Double>
}