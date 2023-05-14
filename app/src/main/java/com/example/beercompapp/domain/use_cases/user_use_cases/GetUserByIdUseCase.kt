package com.example.beercompapp.domain.use_cases.user_use_cases

import com.example.beercompapp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(id: String) = repository.getUserById(id)
}