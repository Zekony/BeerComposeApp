package com.example.beercompapp.domain.use_cases

import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val repository: ProductAppRepository
) {
    suspend operator fun invoke(item: CartItem) {
        repository.deleteCartItem(item)
    }
}