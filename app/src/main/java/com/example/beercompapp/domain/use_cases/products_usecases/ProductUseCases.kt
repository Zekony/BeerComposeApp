package com.example.beercompapp.domain.use_cases.products_usecases

data class ProductUseCases(
    val getBeersFromNetworkUseCase: GetBeersFromNetworkUseCase,
    val getSnacksFromNetworkUseCase: GetSnacksFromNetworkUseCase,
    val getProductByIdFromDbUseCase: GetProductByIdFromDbUseCase,
    val getProductsFromDbUseCase: GetProductsFromDbUseCase,
    val updateProductInDbUseCase: UpdateProductInDbUseCase,
)