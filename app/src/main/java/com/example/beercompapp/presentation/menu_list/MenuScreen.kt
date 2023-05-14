package com.example.beercompapp.presentation.menu_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.BeerAppUiState
import com.example.beercompapp.presentation.DownloadState
import com.example.beercompapp.presentation.MenuCategory
import com.example.beercompapp.presentation.utils.CartButtonHelper
import com.example.beercompapp.presentation.utils.LoadingAnimation3
import com.example.beercompapp.presentation.utils.ToShoppingCartButton

@Composable
fun BeerAppMenuScreen(
    uiState: BeerAppUiState,
    onProductItemClick: (ProductItem) -> Unit,
    onErrorButtonClick: () -> Unit,
    cartHelper: CartButtonHelper,
    onFabClick: (ProductItem) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.downloadState) {
        DownloadState.Loading -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation3()
            }
        }
        DownloadState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 7.5.dp,
                        end = 7.5.dp,
                        top = 70.dp,
                        bottom = 80.dp
                    )
                ) {
                    items(
                        items = when (uiState.menuCategory) {
                            MenuCategory.Beer -> uiState.listOfBeer
                            MenuCategory.Snacks -> uiState.listOfSnacks
                        }, key = { item -> item.UID }) { item ->
                        BeerAppMenuItem(
                            item = item,
                            onProductItemClick = onProductItemClick,
                            cartItem = if (uiState.shoppingCart.isEmpty()) {
                                CartItem()
                            } else {
                                uiState.shoppingCart.firstOrNull { it.UID == item.UID }
                            },
                            cartHelper = cartHelper,
                            onFabClick = { onFabClick(item) }
                        )
                    }
                }
            }
        }
        DownloadState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = androidx.compose.ui.R.string.default_error_message),
                    style = MaterialTheme.typography.h1
                )
                Button(onClick = { run(onErrorButtonClick) }) {
                    Text(
                        text = stringResource(id = R.string.try_again),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BeerAppMenuItem(
    onProductItemClick: (ProductItem) -> Unit,
    cartHelper: CartButtonHelper,
    onFabClick: () -> Unit,
    item: ProductItem,
    cartItem: CartItem?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .height(280.dp)
            .width(190.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onProductItemClick(item) },
        elevation = 12.dp,
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Box() {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(BASE_URL + item.imagePath)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.error_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    contentDescription = item.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(182.dp)
                )
                FloatingActionButton(
                    onClick = onFabClick,
                    shape = CircleShape,
                    containerColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(30.dp)
                        .offset(x = -(15).dp, y = 15.dp)
                ) {
                    if (item.isFavorite) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic__filled_heart),
                            contentDescription = "Like"
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_empty_heart),
                            contentDescription = "Not Like"
                        )
                    }
                }
            }
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
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "${item.price} ₽",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                ToShoppingCartButton(
                    productItem = item,
                    cartItem = cartItem,
                    cartHelper = cartHelper
                )
            }
        }
    }
}


@Preview
@Composable
fun BeerAppMenuItemPreview() {
    BeerAppMenuItem(
        item = LocalDataProvider.getProductsTestList()[1],
        onProductItemClick = {},
        cartItem = LocalDataProvider.getCartItemsTestList()[1],
        cartHelper = object : CartButtonHelper {
            override fun addToCart(cartItem: CartItem) {}
            override fun updateCartItem(cartItem: CartItem) {}
            override fun deleteCartItem(cartItem: CartItem) {}
        },
        onFabClick = {}
    )
}