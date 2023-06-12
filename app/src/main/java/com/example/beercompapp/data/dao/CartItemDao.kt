package com.example.beercompapp.data.dao

import androidx.room.*
import com.example.beercompapp.data.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(item: CartItem)

    @Upsert
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM CartTable WHERE UID = :UID")
    suspend fun deleteCartItemByUID(UID: String)

    @Query("SELECT * FROM CartTable")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM CartTable WHERE UID = :id")
    fun getCartItemById(id: String): Flow<CartItem>

    @Query("DELETE FROM CartTable")
    suspend fun emptyCart()
}