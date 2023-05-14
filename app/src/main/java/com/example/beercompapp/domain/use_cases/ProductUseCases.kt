package com.example.beercompapp.domain.use_cases

data class ProductUseCases(
    val getBeersFromNetworkUseCase: GetBeersFromNetworkUseCase,
    val getSnacksFromNetworkUseCase: GetSnacksFromNetworkUseCase,
    val getProductByIdFromDbUseCase: GetProductByIdFromDbUseCase,
    val getProductsFromDbUseCase: GetProductsFromDbUseCase,
    val updateProductInDbUseCase: UpdateProductInDbUseCase,
    val updateCartItemUseCase: UpdateCartItemUseCase,
    val addToCartUseCase: AddToCartUseCase,
    val getCartItemsFromDbUseCase: GetCartItemsFromDbUseCase,
    val deleteCartItemUseCase: DeleteCartItemUseCase
)