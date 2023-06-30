package com.example.beercompapp.domain.use_cases.products_usecases

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsFromDbUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<ProductItem>> {
        return repository.getProducts()
    }
}