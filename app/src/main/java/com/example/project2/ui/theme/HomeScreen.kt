package com.example.project2.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project2.R
import com.example.project2.StoreTopAppBar
import com.example.project2.model.Article
import com.example.project2.ui.theme.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToItems: (Int) -> Unit,
    onNavigateToCart: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            StoreTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                navigateToCart = onNavigateToCart,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        if (isPortrait) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    CategoryCard(
                        imageResId = R.drawable.all_products,
                        titleResId = R.string.all_products,
                        onClick = {
                            homeViewModel.getItems(1)
                            navigateToItems(1)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.electronics,
                        titleResId = R.string.electronics,
                        onClick = {
                            homeViewModel.getItems(2)
                            navigateToItems(2)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.jewelery,
                        titleResId = R.string.jewelery,
                        onClick = {
                            homeViewModel.getItems(3)
                            navigateToItems(3)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.mens_clothes,
                        titleResId = R.string.mens_clothing,
                        onClick = {
                            homeViewModel.getItems(4)
                            navigateToItems(4)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.womens_clothes,
                        titleResId = R.string.womens_clothing,
                        onClick = {
                            homeViewModel.getItems(5)
                            navigateToItems(5)
                        }
                    )
                }
            }
        }else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                item {
                    CategoryCard(
                        imageResId = R.drawable.all_products,
                        titleResId = R.string.all_products,
                        onClick = {
                            homeViewModel.getItems(1)
                            navigateToItems(1)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.electronics,
                        titleResId = R.string.electronics,
                        onClick = {
                            homeViewModel.getItems(2)
                            navigateToItems(2)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.jewelery,
                        titleResId = R.string.jewelery,
                        onClick = {
                            homeViewModel.getItems(3)
                            navigateToItems(3)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.mens_clothes,
                        titleResId = R.string.mens_clothing,
                        onClick = {
                            homeViewModel.getItems(4)
                            navigateToItems(4)
                        }
                    )
                }
                item {
                    CategoryCard(
                        imageResId = R.drawable.womens_clothes,
                        titleResId = R.string.womens_clothing,
                        onClick = {
                            homeViewModel.getItems(5)
                            navigateToItems(5)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(imageResId: Int, titleResId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp, top = 16.dp)
            .fillMaxWidth(),
        elevation =  CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color(0xFFE8C7F0)),
        onClick = {
            onClick()
        }
    ) {
        Column {
            Image(
                painter = painterResource(imageResId),
                contentDescription = stringResource(titleResId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(titleResId),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
        }
    }
}


