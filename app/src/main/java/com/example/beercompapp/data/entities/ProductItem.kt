package com.example.beercompapp.data.entities

//@Entity(tableName = "ProductTable")
data class ProductItem(
    //@PrimaryKey
    val UID: String = "",
    val alcPercentage: Double = 0.0,
    val description: String = "",
    val imagePath: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val type: String = "",
    val category: String = "",
    val volume: Double? = 0.0,
    var isFavorite: Boolean = false,
    val salePercentage: Int? = 0,
    val weight: Int? = 0,
    val tags: List<String>? = emptyList()
)