package com.example.beercompapp.data.repository

import com.example.beercompapp.data.db.UserDao
import com.example.beercompapp.data.entities.User
import com.example.beercompapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {
    override suspend fun addUser(user: User) = dao.addUser(user)

    override suspend fun updateUser(user: User) = dao.updateUser(user)

    override suspend fun deleteUser(user: User) = dao.deleteUser(user)

    override fun getUserById(id: String) = dao.getUserById(id)
}