package com.example.beercompapp.domain.use_cases

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsFromDbUseCase @Inject constructor(
    private val repository: ProductAppRepository
) {
    operator fun invoke(): Flow<List<ProductItem>> {
        return repository.getProducts()
    }
}