package com.example.project2.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.data.ArticleRepository
import com.example.project2.model.Article
import com.example.project2.network.StoreApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ShopUiState {
    data class Success(val items: List<Article>) : ShopUiState
    object Error : ShopUiState
    object Loading : ShopUiState
}

class HomeViewModel: ViewModel() {
    var shopUiState: ShopUiState by mutableStateOf(ShopUiState.Loading)
        private set

    init {
        getItems(1)
    }

    fun getItems(category: Int) {
        viewModelScope.launch {
            shopUiState = ShopUiState.Loading
            shopUiState = try {
                val listResult = when (category) {
                     1 -> StoreApi.retrofitService.getArticles()
                     2 -> StoreApi.retrofitService.getArticlesElectronics()
                     3 -> StoreApi.retrofitService.getArticlesJewelery()
                     4 -> StoreApi.retrofitService.getArticlesMens()
                     5 -> StoreApi.retrofitService.getArticlesWomens()
                     else -> emptyList()
                }
                ShopUiState.Success(listResult)
            } catch (e: IOException) {
                ShopUiState.Error
            } catch (e: HttpException) {
                ShopUiState.Error
            }
        }
    }

}