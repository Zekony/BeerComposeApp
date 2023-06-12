package com.example.beercompapp.domain.repository

import com.example.beercompapp.data.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartItemRepository {

    fun getCartItems(): Flow<List<CartItem>>

    suspend fun addToCart(item: CartItem)

    suspend fun updateCartItem(item: CartItem)

    suspend fun deleteCartItem(item: CartItem)

    suspend fun emptyCart()
}