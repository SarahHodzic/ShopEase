package com.example.project2.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project2.R
import com.example.project2.StoreTopAppBar
import com.example.project2.model.Article
import com.example.project2.ui.theme.navigation.NavigationDestination

object CategoryDestination : NavigationDestination {
    override val route = "category"
    override val titleRes = R.string.app_name
}

val categoryTitles = mapOf(
    1 to R.string.all_products,
    2 to R.string.electronics,
    3 to R.string.jewelery,
    4 to R.string.mens_clothing,
    5 to R.string.womens_clothing
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    category: Int,
    shopUiState: ShopUiState,
    onNavigateUp: () -> Unit,
    navigateToArticleDetails: (Article) -> Unit,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val titleRes = categoryTitles[category] ?: R.string.app_name

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            StoreTopAppBar(
                title = stringResource(titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                navigateToCart = onNavigateToCart,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        when (shopUiState) {
            is ShopUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is ShopUiState.Success -> ResultScreen(
                 shopUiState.items,
                 onArticleClick = navigateToArticleDetails,
                 modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
            is ShopUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
        }

    }
}



@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}


@Composable
fun ResultScreen(items: List<Article>, onArticleClick: (Article) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            ArticleItem(article = item, onArticleClick = onArticleClick)
        }
    }
}

@Composable
fun ArticleItem(article: Article, onArticleClick: (Article) -> Unit) {
    val formattedPrice = String.format("$%.2f", article.price.toFloat())
    Card(
        elevation =  CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onArticleClick(article) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD49AE3))
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.shop_item_photo),
                modifier = Modifier
                    .size(80.dp)  // Postavlja fiksnu veličinu za slike
                    .clip(RoundedCornerShape(8.dp))  // Zaobljeni uglovi (opciono)
                //contentScale = ContentScale.Crop,
                //modifier = modifier,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = article.category,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = formattedPrice,
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }
}