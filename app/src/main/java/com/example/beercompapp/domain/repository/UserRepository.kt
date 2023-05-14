package com.example.beercompapp.domain.repository

import com.example.beercompapp.data.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

    fun getUserById(id: String): Flow<User>
}