package com.example.beercompapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beercompapp.common.converters.MenuCategoryConverter
import com.example.beercompapp.data.dao.CartItemDao
import com.example.beercompapp.data.dao.LikesDao
import com.example.beercompapp.data.dao.ProductDao
import com.example.beercompapp.data.dao.UserDao
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.UserEntity
import com.example.beercompapp.data.entities.relations.UserProductItemLikes

@Database(
    entities = [
        ProductItem::class,
        CartItem::class,
        UserEntity::class,
        UserProductItemLikes::class
    ],
    version = 1
)
@TypeConverters(MenuCategoryConverter::class)
abstract class BeerAppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun userDao(): UserDao
    abstract fun likesDao(): LikesDao
}