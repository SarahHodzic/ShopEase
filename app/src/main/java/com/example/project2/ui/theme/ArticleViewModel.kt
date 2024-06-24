package com.example.project2.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.data.ArticleItem
import com.example.project2.data.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleViewModel (private val articleRepository: ArticleRepository): ViewModel(){

    private val _articles: MutableStateFlow<List<ArticleItem>> = MutableStateFlow(emptyList())
    val articles: StateFlow<List<ArticleItem>> = _articles

    private val _totalPrice: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun showDialogFun(){
        _showDialog.value = !_showDialog.value
    }

    init {
        fetchArticles()
    }


    private fun fetchArticles() {
        viewModelScope.launch {
            articleRepository.getAllItemsStream().collect { items ->
                _articles.value = items
                updateTotalPrice()
            }
        }
    }

    private suspend fun updateTotalPrice() {

        val priceFlow = articleRepository.getPrice()

        priceFlow.collect { price ->
            _totalPrice.value = price
        }
    }


    suspend fun sortByPriceDesc() {
        viewModelScope.launch {
            articleRepository.getAllItemsDesc().collect { items ->
                _articles.value = items
            }
        }
    }
    suspend fun sortByPriceAsc() {
        viewModelScope.launch {
            articleRepository.getAllItemsAsc().collect { items ->
                _articles.value = items
            }
        }
    }


    suspend fun addToCart(article: ArticleItem) {
        articleRepository.insertItem(article)
    }

    suspend fun deleteArticle(article: ArticleItem) {
        articleRepository.deleteItem(article)
        fetchArticles()
    }

    suspend fun clearAllArticles(){
        viewModelScope.launch {
            articleRepository.clearAllArticles()
            fetchArticles()
        }
    }

}