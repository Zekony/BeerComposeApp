package com.example.beercompapp.domain.use_cases.user_use_cases

import com.example.beercompapp.data.entities.User
import com.example.beercompapp.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.addUser(user)
    }
}