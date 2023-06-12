package com.example.beercompapp.presentation.shopping_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.beercompapp.R
import com.example.beercompapp.common.Constants
import com.example.beercompapp.data.LocalDataProvider
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.presentation.utils.CartButtonHelper
import com.example.beercompapp.presentation.utils.ShoppingCartButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingCartViewModel = hiltViewModel()
) {
    val cartList = viewModel.state.collectAsState().value.shoppingCart

    if (cartList.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp,
                top = 65.dp,
                bottom = 100.dp
            ),
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            items(cartList, key = { item -> item.UID }) {
                ShoppingListItem(
                    item = it,
                    cartHelper = viewModel.shoppingCartButtonHelper,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.cart_is_empty),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h1
            )
        }
    }
}


@Composable
fun ShoppingListItem(
    item: CartItem,
    cartHelper: CartButtonHelper,
    modifier: Modifier = Modifier
) {
    // код для возможности свайпать предметы для удаления, к сожалению не работает вместе с анимацией
/*    val swipe = SwipeAction(
        onSwipe = {
            cartHelper.deleteCartItem(item)
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = MaterialTheme.colors.surface
            )
        },
        background = MaterialTheme.colors.surface
    )
    SwipeableActionsBox(
        swipeThreshold = 50.dp,
        endActions = listOf(swipe),
        backgroundUntilSwipeThreshold = MaterialTheme.colors.surface
    ) {*/
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(Constants.BASE_URL + item.imagePath)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.error_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .size(100.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = item.name, style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                ShoppingCartButton(
                    cartItem = item,
                    cartHelper = cartHelper,
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                androidx.compose.material3.Text(text = "${item.price} ₽")
                androidx.compose.material3.Text(
                    text = "${item.price * item.amount} ₽",
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}


@Preview
@Composable
fun ShoppingListItemPreview() {
    ShoppingListItem(
        item = LocalDataProvider.getCartItemsTestList()[1],
        cartHelper = object : CartButtonHelper {
            override fun addToCart(cartItem: CartItem) {}
            override fun updateCartItem(cartItem: CartItem) {}
            override fun deleteCartItem(cartItem: CartItem) {}
        })
}
