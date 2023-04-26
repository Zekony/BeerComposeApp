package com.example.beercompapp.presentation

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.utils.BeerPage

data class BeerAppUiState(
    val currentTab: BeerPage = BeerPage.MainScreen,
    val menuCategory: MenuCategory = MenuCategory.Beer,
    val downloadState: DownloadState = DownloadState.Loading,
    val listOfSnacks: List<ProductItem> = mutableListOf(),
    val listOfBeer: List<ProductItem> = mutableListOf(),
    val error: String = ""
)

sealed interface DownloadState {
    object Success : DownloadState
    object Loading : DownloadState
    object Error : DownloadState
}

sealed interface MenuCategory {
    object Beer : MenuCategory
    object Snacks : MenuCategory
}