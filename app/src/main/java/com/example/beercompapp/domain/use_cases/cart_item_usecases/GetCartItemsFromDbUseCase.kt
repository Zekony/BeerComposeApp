package com.example.beercompapp.domain.use_cases.cart_item_usecases

import android.util.Log
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.repository.CartItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsFromDbUseCase @Inject constructor(
    private val repository: CartItemRepository
) {

    operator fun invoke(): Flow<List<CartItem>> {
        Log.d("MSViewModel", "GetCartItemsFromDBUseCase is called")
        return repository.getCartItems()
    }
}