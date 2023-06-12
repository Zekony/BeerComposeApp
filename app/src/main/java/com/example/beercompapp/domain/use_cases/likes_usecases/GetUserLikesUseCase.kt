package com.example.beercompapp.domain.use_cases.likes_usecases

import com.example.beercompapp.data.entities.relations.UserWithProducts
import com.example.beercompapp.domain.repository.LikesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLikesUseCase @Inject constructor(
    private val repository: LikesRepository
) {
    operator fun invoke(number: String): Flow<UserWithProducts> {
        return repository.getUserLikes(number)
    }
}