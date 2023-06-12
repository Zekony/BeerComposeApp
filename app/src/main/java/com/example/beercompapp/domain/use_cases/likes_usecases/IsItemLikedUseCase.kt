package com.example.beercompapp.domain.use_cases.likes_usecases

import com.example.beercompapp.domain.repository.LikesRepository
import javax.inject.Inject

class IsItemLikedUseCase @Inject constructor(
    private val repository: LikesRepository
) {
    suspend operator fun invoke(UID: String): Boolean {
        return repository.isItemLiked(UID)
    }
}