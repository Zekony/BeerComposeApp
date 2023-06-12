package com.example.beercompapp.data.dao

import androidx.room.*
import com.example.beercompapp.data.entities.relations.UserProductItemLikes
import com.example.beercompapp.data.entities.relations.UserWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface LikesDao {

    @Transaction
    @Query("SELECT * FROM UserTable WHERE phoneNumber = :number")
    fun getUserLikes(number: String): Flow<UserWithProducts>

    @Query("SELECT EXISTS(SELECT 1 FROM LikesTable WHERE UID = :UID)")
    suspend fun isItemLiked(UID: String): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addLike(userProductItem: UserProductItemLikes)

    @Query("DELETE FROM LikesTable WHERE phoneNumber = :number AND UID = :UID")
    suspend fun removeLike(UID: String, number: String)
}