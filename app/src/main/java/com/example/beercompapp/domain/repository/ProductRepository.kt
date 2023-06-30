package com.example.beercompapp.domain.repository

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.network.dto.BeerDtoList
import com.example.beercompapp.data.network.dto.SnackDtoList
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getBeersApi(): BeerDtoList?

    suspend fun getSnacksApi(): SnackDtoList?

    fun getProducts(): Flow<List<ProductItem>>

    fun getProductById(id: String): Flow<ProductItem>

    suspend fun addProduct(item: ProductItem)

    suspend fun updateProduct(item: ProductItem)
}