package com.example.project2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "articles")
data class ArticleItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val price: Double,
    val image: String,
    val category: String,
    val rating: Double,
)

