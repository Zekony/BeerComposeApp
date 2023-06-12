package com.example.beercompapp.domain.use_cases.products_usecases

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import javax.inject.Inject

class UpdateProductInDbUseCase @Inject constructor(
    private val repository: ProductAppRepository
) {

    suspend operator fun invoke(item: ProductItem) {
        val newItem = item.copy()
        return repository.updateProduct(newItem)
    }
}