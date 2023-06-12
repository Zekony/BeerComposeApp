package com.example.beercompapp.data.repository

import android.util.Log
import com.example.beercompapp.data.dao.CartItemDao
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.repository.CartItemRepository
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val dao: CartItemDao
) : CartItemRepository {

    override fun getCartItems() = dao.getCartItems()

    override suspend fun addToCart(item: CartItem) {
        dao.addToCart(item)
        Log.d("ProductRepositoryImpl", "addToCart is called")
    }

    override suspend fun updateCartItem(item: CartItem) {
        dao.updateCartItem(item)
        Log.d("ProductRepositoryImpl", "updateCartItem is called")
    }

    override suspend fun deleteCartItem(item: CartItem) {
        dao.deleteCartItem(item)
        Log.d("ProductRepositoryImpl", "deleteCartItem is called")
    }

    override suspend fun emptyCart() {
        dao.emptyCart()
        Log.d("ProductRepositoryImpl", "emptyCart is called")
    }
}