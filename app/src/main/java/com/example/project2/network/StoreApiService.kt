package com.example.project2.network

import com.example.project2.model.Article
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


private const val BASE_URL =
    "https://fakestoreapi.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface StoreApiService {
    @GET("products")
    suspend fun getArticles(): List<Article>

    @GET("products/category/jewelery")
    suspend fun getArticlesJewelery(): List<Article>

    @GET("products/category/electronics")
    suspend fun getArticlesElectronics(): List<Article>

    @GET("products/category/men%27s%20clothing")
    suspend fun getArticlesMens(): List<Article>

    @GET("products/category/women%27s%20clothing")
    suspend fun getArticlesWomens(): List<Article>

    @GET("products/1")
    suspend fun getArticleById(id: Int): Article
}

object StoreApi {
    val retrofitService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }
}