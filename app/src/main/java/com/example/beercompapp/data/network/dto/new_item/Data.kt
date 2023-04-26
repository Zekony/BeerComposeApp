package com.example.beercompapp.data.network.dto.new_item

data class Data(
    val UID: String = "",
    val createdAt: String = "",
    val description: String = "",
    val imagePath: String = "",
    val name: String = "",
    val price: Int = 0,
    val tags: String = "",
    val type: String = "",
    val updatedAt: String = "",
    val weight: Int = 0
)