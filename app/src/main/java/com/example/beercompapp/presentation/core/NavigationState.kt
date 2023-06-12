package com.example.beercompapp.presentation.core

import com.example.beercompapp.presentation.utils.BeerPage

data class NavigationState(
    val currentTab: BeerPage = BeerPage.MainScreen,
    val menuCategory: MenuCategory = MenuCategory.Beer,
)

sealed interface MenuCategory {
    object Beer : MenuCategory
    object Snacks : MenuCategory
}