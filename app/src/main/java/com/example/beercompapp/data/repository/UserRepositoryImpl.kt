package com.example.beercompapp.data.repository

import com.example.beercompapp.data.dao.UserDao
import com.example.beercompapp.data.entities.UserEntity
import com.example.beercompapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {
    override suspend fun addUser(user: UserEntity) = dao.addUser(user)

    override suspend fun updateUser(user: UserEntity) = dao.updateUser(user)

    override suspend fun deleteUser(user: UserEntity) = dao.deleteUser(user)

    override suspend fun getUserByNumber(number: String): UserEntity = dao.getUserById(number)
}