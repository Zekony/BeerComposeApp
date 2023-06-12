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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.beercompapp.R
import com.example.beercompapp.common.Constants.BASE_URL
import com.example.beercompapp.data.LocalDataProvider
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.core.MenuCategory
import com.example.beercompapp.presentation.utils.CartButtonHelper
import com.example.beercompapp.presentation.utils.LoadingAnimation3
import com.example.beercompapp.presentation.utils.ShoppingCartButton

@Composable
fun BeerAppMenuScreen(
    viewModel: MenuScreenViewModel = hiltViewModel(),
    toItemDetailScreen: (ProductItem) -> Unit,
    currentCategory: MenuCategory,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.state.collectAsState().value

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
                    .fillMaxSize()
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
                        items = uiState.listOfProducts.filter { it.category == currentCategory },
                        key = { item -> item.UID }) { item ->
                        BeerAppMenuItem(
                            item = item,
                            onProductItemClick = toItemDetailScreen,
                            cartItem = if (uiState.shoppingCart.isEmpty()) {
                                CartItem()
                            } else {
                                uiState.shoppingCart.firstOrNull { it.UID == item.UID }
                            },
                            isItemLiked = uiState.userLikes.contains(item),
                            cartHelper = viewModel.shoppingCartButtonHelper,
                            isUserActive = uiState.user.login != "",
                            onFabClick = { viewModel.likeOrDislike(item) }
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
                    text = stringResource(id = R.string.loading_error),
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center
                )
                Button(onClick = viewModel::getProductsFromDb) {
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
    isItemLiked: Boolean,
    isUserActive: Boolean,
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
            Box {
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
                    containerColor = if (isUserActive) MaterialTheme.colors.background else Color.LightGray,
                    contentColor = if (isUserActive) MaterialTheme.colors.primary else Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(30.dp)
                        .offset(x = -(15).dp, y = 15.dp)
                ) {
                    if (isItemLiked) {
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
                ShoppingCartButton(
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
        onFabClick = {},
        isItemLiked = true,
        isUserActive = false
    )
}