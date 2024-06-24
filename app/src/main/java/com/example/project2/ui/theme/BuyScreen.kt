package com.example.project2.ui.theme

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project2.R
import com.example.project2.StoreTopAppBar
import com.example.project2.data.ArticleItem
import com.example.project2.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch

object BuyDestination : NavigationDestination {
    override val route = "Cart"
    override val titleRes = R.string.cart
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: ArticleViewModel
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val articles by viewModel.articles.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val totalPrice by viewModel.totalPrice.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            StoreTopAppBar(
                title = stringResource(BuyDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp,
                isOnCart = true
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sort buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch{
                            viewModel.sortByPriceDesc()
                        } },
                    colors = ButtonDefaults.buttonColors(Color(0xFF4e0561)),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.sort_high_to_low))
                }
                Button(
                    onClick = {
                        coroutineScope.launch{
                            viewModel.sortByPriceAsc()
                        }
                              },
                    colors = ButtonDefaults.buttonColors(Color(0xFF4e0561)),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.sort_low_to_high))
                }
            }

            Text(
                text = "Total Price: $totalPrice",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(
                onClick = { viewModel.showDialogFun() },
                colors = ButtonDefaults.buttonColors(Color(0xFF4e0561)),
                modifier = Modifier.padding(top = 16.dp),
                enabled = articles.isNotEmpty()
            ) {
                Text(text = stringResource(id = R.string.buy))
            }

            // List of articles
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(articles) { article ->
                    ArticleCard(
                        article = article,
                        onDeleteClicked = {
                            coroutineScope.launch {
                                viewModel.deleteArticle(article)
                            }
                        }
                        )
                    Divider()
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.showDialogFun() },
                    title = { Text(text = stringResource(id = R.string.confirm_purchase)) },
                    text = {
                        Column {
                            Text(text = "Total Price: $totalPrice")
                            Spacer(modifier = Modifier.height(16.dp))
                            IconButton(
                                onClick = {
                                    val shareIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, articles.joinToString(", ") { it.title })
                                    }

                                    context.startActivity(
                                        Intent.createChooser(
                                            shareIntent,
                                            null
                                        )
                                    )
                                }
                            ) {
                                Icon(Icons.Default.Share, contentDescription = stringResource(id = R.string.share))
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.clearAllArticles()
                                }
                                viewModel.showDialogFun()
                                navigateToHomeScreen()
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF4e0561)),
                        ) {
                            Text(text = stringResource(id = R.string.buy))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { viewModel.showDialogFun()},
                            colors = ButtonDefaults.buttonColors(Color(0xFF4e0561)),
                        ) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun ArticleCard(article: ArticleItem,onDeleteClicked: () -> Unit) {
    Card(
        elevation =  CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Price: $${article.price}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    IconButton(
                        onClick = onDeleteClicked,
                        modifier = Modifier
                            .size(48.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete),
                                tint = Color.White
                            )
                        }
                    )
                }
            }
        }
    }
}