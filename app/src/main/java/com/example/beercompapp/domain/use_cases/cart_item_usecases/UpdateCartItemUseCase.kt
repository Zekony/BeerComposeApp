package com.example.beercompapp.domain.use_cases.cart_item_usecases

import android.util.Log
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.repository.CartItemRepository
import javax.inject.Inject

class UpdateCartItemUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke(item: CartItem) {
        Log.d("MSViewModel", "UpdateCartItemUseCase is called")
        repository.updateCartItem(item)
    }
}