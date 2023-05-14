package com.example.beercompapp.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.beercompapp.R
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.productToCartItem

@Composable
fun ToShoppingCartButton(
    productItem: ProductItem? = null,
    cartItem: CartItem?,
    cartHelper: CartButtonHelper ,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(25.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                cartHelper.addToCart(productToCartItem(productItem!!))
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(Color.Green),

            ) {
            if (cartItem == null || cartItem.amount == 0) {
                Text(
                    text = stringResource(id = R.string.add_to_cart),
                    style = MaterialTheme.typography.button,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = stringResource(
                        id = R.string.shopping
                    ),
                    modifier = Modifier.clickable {
                        if (cartItem.amount.dec() == 0) {
                            cartHelper.deleteCartItem(cartItem)
                        } else {
                            cartHelper.updateCartItem(cartItem.copy(amount = cartItem.amount.dec()))
                        }
                    }
                )
                Text(text = cartItem.amount.toString())
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(
                        id = R.string.shopping
                    ),
                    modifier = Modifier.clickable {
                        cartHelper.updateCartItem(cartItem.copy(amount = cartItem.amount.inc()))
                    }
                )
            }
        }

    }
}


interface CartButtonHelper {
    fun addToCart(cartItem: CartItem)
    fun updateCartItem(cartItem: CartItem)
    fun deleteCartItem(cartItem: CartItem)
}

