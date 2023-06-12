package com.example.beercompapp.domain.use_cases.user_usecases

import com.example.beercompapp.data.entities.UserEntity
import com.example.beercompapp.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: UserEntity) {
        repository.deleteUser(user)
    }
}