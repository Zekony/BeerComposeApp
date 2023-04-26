package com.example.beercompapp.presentation.menu_list.models

enum class ProductCategory {
    BEER, SNACK;

    fun toRawString(): String {
        return when (this){
            BEER -> "Dark"
            SNACK -> "Light"
        }
    }
}