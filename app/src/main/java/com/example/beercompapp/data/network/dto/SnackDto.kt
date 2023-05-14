package com.example.beercompapp.data.network.dto

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.MenuCategory

data class SnackDto(
    val UID: String = "",
    val createdAt: String = "",
    val description: String = "",
    val imagePath: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val tags: String? = null,
    val type: String = "",
    val updatedAt: String = "",
    val weight: Int? = null
)

fun SnackDto.toProductItem(): ProductItem {
    return ProductItem(
        UID = UID,
        description = description,
        imagePath = imagePath,
        name = name,
        price = price,
        type = type,
        category = MenuCategory.Snacks,
        weight = weight,
    )
}