package com.example.beercompapp.data.db

import androidx.room.*
import com.example.beercompapp.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Upsert
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM UserTable WHERE number = :number")
    fun getUserById(number: String): Flow<User>
}
