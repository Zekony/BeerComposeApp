package com.example.beercompapp.domain.use_cases.likes_usecases

import com.example.beercompapp.domain.repository.LikesRepository
import javax.inject.Inject

class RemoveLikeUseCase @Inject constructor(
    private val repository: LikesRepository
) {
    suspend operator fun invoke(UID: String, number: String) {
        repository.removeLike(UID, number)
    }
}