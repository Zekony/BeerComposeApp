package com.example.beercompapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beercompapp.common.converters.MenuCategoryConverter
import com.example.beercompapp.common.converters.StringListConverter
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.User

@Database(
    entities = [ProductItem::class, CartItem::class, User::class],
    version = 2
)
@TypeConverters(StringListConverter::class, MenuCategoryConverter::class)
abstract class BeerAppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun userDao(): UserDao
}