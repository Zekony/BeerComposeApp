package com.example.beercompapp.data.dao

import androidx.room.*
import com.example.beercompapp.data.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Upsert
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM UserTable WHERE phoneNumber = :number")
    suspend fun getUserById(number: String): UserEntity
}
