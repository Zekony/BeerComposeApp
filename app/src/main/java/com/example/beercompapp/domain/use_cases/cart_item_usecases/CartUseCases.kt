package com.example.beercompapp.domain.use_cases.cart_item_usecases

data class CartUseCases(
    val addToCartUseCase: AddToCartUseCase,
    val updateCartItemUseCase: UpdateCartItemUseCase,
    val getCartItemsFromDbUseCase: GetCartItemsFromDbUseCase,
    val deleteCartItemUseCase: DeleteCartItemUseCase,
    val emptyCartUseCase: EmptyCartUseCase,
)