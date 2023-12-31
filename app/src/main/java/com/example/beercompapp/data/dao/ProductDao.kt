package com.example.beercompapp.data.dao

import androidx.room.*
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(item: ProductItem)

    @Upsert
    suspend fun updateProduct(item: ProductItem)

    @Query("SELECT * FROM ProductTable")
    fun getProducts(): Flow<List<ProductItem>>

    @Query("SELECT * FROM ProductTable WHERE UID = :id")
    fun getProductById(id: String): Flow<ProductItem>
}