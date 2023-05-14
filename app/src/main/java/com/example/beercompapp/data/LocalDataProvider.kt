package com.example.beercompapp.data

import com.example.beercompapp.R
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.MenuCategory

object LocalDataProvider {
    fun getAdsList(): List<Int> {
        return listOf(
            R.drawable.beer_ad_job,
            R.drawable.beer_ad_safe,
            R.drawable.beer_ad_prom,
            R.drawable.beer_ad_serf
        )
    }

    fun getProductsTestList(): List<ProductItem> {
        return listOf(
            ProductItem(
                UID = "asdfqwefas",
                alcPercentage = 0.0,
                description = "Пивчики – это свиные сырокопченые тонкие колбаски со специями",
                imagePath = "images//snacks//1681929915128-pivchiky.jpg",
                name = "Yablonski",
                price = 110.0,
                type = "",
                category = MenuCategory.Beer,
                volume = 7.0,
                isFavorite = false,
                salePercentage = 30,
                weight = 10,
                tags = emptyList()
            ),
            ProductItem(
                UID = "asdvqwed",
                alcPercentage = 0.0,
                description = "Его вкус напоминает филе хариуса.",
                imagePath = "images//snacks//1681928336693-omul_baikalskiy.jpg",
                name = "Yablonski",
                price = 110.0,
                type = "",
                category = MenuCategory.Beer,
                volume = 7.0,
                isFavorite = false,
                salePercentage = 30,
                weight = 10,
                tags = emptyList()
            )
        )
    }

    fun getCartItemsTestList(): List<CartItem> {
        return listOf(
            CartItem(
                UID = "asdfqwefas",
                price = 110.0,
                name = "Yablonski",
                imagePath = "images//snacks//1681928336693-omul_baikalskiy.jpg",
                amount = 3
            ),
            CartItem(
                UID = "asdvqwed",
                price = 110.0,
                imagePath = "images//snacks//1681929915128-pivchiky.jpg",
                name = "Pivchiki",
                amount = 5
            )
        )
    }
}