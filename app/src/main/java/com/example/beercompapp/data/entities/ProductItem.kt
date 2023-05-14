package com.example.beercompapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.beercompapp.common.converters.MenuCategoryConverter
import com.example.beercompapp.common.converters.StringListConverter
import com.example.beercompapp.presentation.MenuCategory

@Entity(tableName = "ProductTable")
data class ProductItem(
    @PrimaryKey
    val UID: String = "",
    val alcPercentage: Double = 0.0,
    val description: String = "",
    val imagePath: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val type: String = "",
    @TypeConverters(MenuCategoryConverter::class)
    val category: MenuCategory,
    val volume: Double? = 0.0,
    var isFavorite: Boolean = false,
    val salePercentage: Int? = 0,
    val weight: Int? = 0,
    @TypeConverters(StringListConverter::class)
    val tags: List<String>? = emptyList()
)