package com.example.beercompapp.presentation.menu_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.beercompapp.R
import com.example.beercompapp.common.Constants.BASE_URL
import com.example.beercompapp.data.LocalDataProvider
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.BeerAppUiState
import com.example.beercompapp.presentation.DownloadState
import com.example.beercompapp.presentation.MenuCategory
import com.example.beercompapp.presentation.utils.BeerPage
import com.example.beercompapp.presentation.utils.LoadingAnimation3
import com.example.beercompapp.presentation.utils.NavigationItemContent

@Composable
fun BeerAppMenuScreen(
    listOfBeer: List<ProductItem>,
    listOfSnacks: List<ProductItem>,
    uiState: BeerAppUiState,
    onProductItemClick: (ProductItem) -> Unit,
    onTabPressed: (BeerPage) -> Unit,
    onErrorButtonClick: () -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {

    when (uiState.downloadState) {
        DownloadState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation3()
            }
        }
        DownloadState.Success -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 7.5.dp,
                        end = 7.5.dp,
                        top = 70.dp,
                        bottom = 80.dp
                    ),
                    //horizontalArrangement = Arrangement.spacedBy(4.dp),

                ) {
                    items(
                        items = if (uiState.menuCategory == MenuCategory.Beer) {
                            listOfBeer
                        } else {
                            listOfSnacks
                        }, key = { item -> item.UID }) { item ->
                        BeerAppMenuItem(item = item, onProductItemClick = onProductItemClick)
                    }
                }
            }
        }
        DownloadState.Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.error,
                    style = MaterialTheme.typography.h1
                )
                Button(onClick = { run(onErrorButtonClick) }) {
                    Text(text = stringResource(id = R.string.try_again), color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun BeerAppMenuItem(
    onProductItemClick: (ProductItem) -> Unit,
    item: ProductItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .size(270.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onProductItemClick(item) },
        elevation = 12.dp,
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BASE_URL + item.imagePath)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.error_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = item.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(175.dp)
            )
            Text(
                text = item.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(8.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.padding(8.dp)
            ) {
                Text(
                    text = "${item.price} ₽",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

            }
        }
    }
}

@Preview
@Composable
fun BeerAppMenuItemPreview() {
    BeerAppMenuItem(item = LocalDataProvider.getProductsTestList()[1], onProductItemClick = {})
}