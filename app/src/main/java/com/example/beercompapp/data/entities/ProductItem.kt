package com.example.beercompapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.beercompapp.common.Constants.PRODUCT_TABLE_NAME
import com.example.beercompapp.common.converters.MenuCategoryConverter
import com.example.beercompapp.presentation.core.MenuCategory

@Entity(tableName = PRODUCT_TABLE_NAME)
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
    val weight: Int? = 0,
)