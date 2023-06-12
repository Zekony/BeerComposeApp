package com.example.beercompapp.domain.repository

import com.example.beercompapp.data.entities.UserEntity

interface UserRepository {

    suspend fun addUser(user: UserEntity)

    suspend fun updateUser(user: UserEntity)

    suspend fun deleteUser(user: UserEntity)

    suspend fun getUserByNumber(number: String): UserEntity?
}