package com.example.beercompapp.domain.use_cases.cart_item_usecases

import android.util.Log
import com.example.beercompapp.domain.repository.CartItemRepository
import javax.inject.Inject

class EmptyCartUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke() {
        Log.d("EmptyCartUseCase", "EmptyCartUseCase is called")
        repository.emptyCart()
    }
}