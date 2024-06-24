package com.example.project2.ui.theme.navigation

import DefaultArticleRepository
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project2.data.ArticleDatabase
import com.example.project2.ui.theme.ArticleDeatilDestination
import com.example.project2.ui.theme.ArticleDetailsScreen
import com.example.project2.ui.theme.ArticleViewModel
import com.example.project2.ui.theme.BuyDestination
import com.example.project2.ui.theme.BuyScreen
import com.example.project2.ui.theme.CategoryDestination
import com.example.project2.ui.theme.CategoryScreen
import com.example.project2.ui.theme.HomeDestination
import com.example.project2.ui.theme.HomeScreen
import com.example.project2.ui.theme.HomeViewModel
import com.example.project2.ui.theme.ShopUiState


@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val homeViewModel: HomeViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItems = { category ->
                    homeViewModel.getItems(category)
                    navController.navigate("${CategoryDestination.route}/$category")
                },
                onNavigateToCart = { navController.navigate(BuyDestination.route) }
            )
        }

        composable(route = "${CategoryDestination.route}/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")?.toInt() ?: 1
            CategoryScreen(
                category = category,
                shopUiState = homeViewModel.shopUiState,
                onNavigateUp = { navController.navigateUp()},
                navigateToArticleDetails = { article ->
                    navController.navigate("articleDetails/${article.id}")
                },
                onNavigateToCart = { navController.navigate(BuyDestination.route) }
            )
        }

        composable(route = "articleDetails/{articleId}") { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId")?.toInt() ?: 0
            val context = LocalContext.current
            val database = ArticleDatabase.getDatabase(context)
            val articleRepository = DefaultArticleRepository(database.ArticleDao())
            val article = homeViewModel.shopUiState.let { uiState ->
                if (uiState is ShopUiState.Success) {
                    uiState.items.firstOrNull { it.id == articleId }
                } else {
                    null
                }
            }

            if (article != null) {
                ArticleDetailsScreen(
                    article = article,
                    onNavigateUp = { navController.navigateUp() },
                    onNavigateToCart = { navController.navigate(BuyDestination.route) },
                    articleRepository = articleRepository
                )
            }
        }

        composable(route = BuyDestination.route) {
            val context = LocalContext.current
            val database = ArticleDatabase.getDatabase(context)
            val articleRepository = DefaultArticleRepository(database.ArticleDao())
            BuyScreen(
                onNavigateUp = { navController.navigateUp() },
                viewModel = ArticleViewModel(articleRepository),
                navigateToHomeScreen = {navController.navigate(HomeDestination.route)}
            )
        }
    }
}