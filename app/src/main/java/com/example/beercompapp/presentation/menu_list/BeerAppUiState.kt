package com.example.beercompapp.presentation.menu_list

import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.model.User

data class BeerAppUiState(
    val downloadState: DownloadState = DownloadState.Loading,
    val user: User = User(),
    val listOfProducts: List<ProductItem> = mutableListOf(),
    val shoppingCart: List<CartItem> = mutableListOf(),
    val userLikes: List<ProductItem> = mutableListOf()
)

sealed interface DownloadState {
    object Success : DownloadState
    object Loading : DownloadState
    object Error : DownloadState
}

