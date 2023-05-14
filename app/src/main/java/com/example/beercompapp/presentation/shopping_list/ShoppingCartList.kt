package com.example.beercompapp.presentation.shopping_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.beercompapp.R
import com.example.beercompapp.common.Constants
import com.example.beercompapp.data.LocalDataProvider
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.presentation.BeerAppUiState
import com.example.beercompapp.presentation.utils.CartButtonHelper
import com.example.beercompapp.presentation.utils.ToShoppingCartButton
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShoppingListScreen(
    cartHelper: CartButtonHelper,
    uiState: BeerAppUiState
) {
    val cartList = uiState.shoppingCart

    LazyColumn(
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 65.dp, bottom = 80.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        items(cartList) { item ->
            ShoppingListItem(item = item, cartHelper)
        }
    }
}


@Composable
fun ShoppingListItem(
    item: CartItem,
    cartHelper: CartButtonHelper,
    modifier: Modifier = Modifier
) {
    val swipe = SwipeAction(
        onSwipe = {
            cartHelper.deleteCartItem(item)
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_highlight_off),
                contentDescription = null,
                tint = MaterialTheme.colors.surface
            )
        },
        background = Color.Red
    )
    SwipeableActionsBox(
        swipeThreshold = 50.dp,
        endActions = listOf(swipe),
        backgroundUntilSwipeThreshold = MaterialTheme.colors.surface
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(6.dp)
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
                    ToShoppingCartButton(
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
                    Text(text = "${item.price} ₽")
                    Text(
                        text = "${item.price * item.amount} ₽",
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingListBottomBar(sum: Double) {
    BottomAppBar(elevation = 4.dp, contentPadding = PaddingValues(vertical = 10.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Итого:", style = MaterialTheme.typography.h1)
                Text(text = "$sum ₽", style = MaterialTheme.typography.h1)
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
