package com.example.beercompapp.common.converters

import androidx.room.TypeConverter
import com.example.beercompapp.presentation.MenuCategory

class MenuCategoryConverter {

    @TypeConverter
    fun toMenuCategory(category: String): MenuCategory {
        return if (category.contains("Beer")) {
            MenuCategory.Beer
        } else if (category.contains("Snack")) {
            MenuCategory.Snacks
        } else {
            MenuCategory.Beer
        }
    }

    @TypeConverter
    fun fromMenuCategory(category: MenuCategory): String {
        return when (category) {
            MenuCategory.Beer -> "Beer"
            MenuCategory.Snacks -> "Snack"
        }
    }
}