package com.example.beercompapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartTable")
data class CartItem(
    @PrimaryKey
    val UID: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imagePath: String = "",
    val amount: Int = 0
)

fun productToCartItem(productItem: ProductItem): CartItem {
    return CartItem(
        UID = productItem.UID,
        amount = 1,
        price = productItem.price,
        name = productItem.name,
        imagePath = productItem.imagePath
    )
}