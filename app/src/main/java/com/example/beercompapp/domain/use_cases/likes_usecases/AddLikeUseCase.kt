package com.example.beercompapp.domain.use_cases.likes_usecases

import com.example.beercompapp.data.entities.relations.UserProductItemLikes
import com.example.beercompapp.domain.repository.LikesRepository
import javax.inject.Inject

class AddLikeUseCase @Inject constructor(
    private val repository: LikesRepository
) {
    suspend operator fun invoke(userProductItem: UserProductItemLikes) {
        repository.addLike(userProductItem)
    }
}