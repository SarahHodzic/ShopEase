package com.example.project2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ArticleItem::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun ArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var Instance: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ArticleDatabase::class.java, "article_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}