package com.example.beercompapp.domain.use_cases

import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsFromDbUseCase @Inject constructor(
    private val repository: ProductAppRepository
) {

    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getCartItems()
    }
}