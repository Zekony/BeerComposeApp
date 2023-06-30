package com.example.beercompapp.data.network.dto

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.core.MenuCategory

data class BeerDto(
    val UID: String = "",
    val alcPercentage: Double = 0.0,
    val category: String = "",
    val createdAt: String = "",
    val description: String = "",
    val imagePath: String = "",
    val isAvaliable: Boolean = false,
    val name: String = "",
    val price: Double = 0.0,
    val salePercentage: Int? = null,
    val tags: String = "",
    val type: String = "",
    val updatedAt: String = "",
    val volume: Double? = null
)

fun BeerDto.toProductItem(): ProductItem {
    return ProductItem(
        UID = UID,
        alcPercentage = alcPercentage,
        description = description,
        imagePath = imagePath,
        name = name,
        price = price,
        type = type,
        category = MenuCategory.Beer,
        volume = volume,
    )
}