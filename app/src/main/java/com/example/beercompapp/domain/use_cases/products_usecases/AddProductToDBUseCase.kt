package com.example.beercompapp.domain.use_cases.products_usecases

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductRepository
import javax.inject.Inject

class AddProductToDBUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(item: ProductItem) {
        return repository.addProduct(item)
    }
}