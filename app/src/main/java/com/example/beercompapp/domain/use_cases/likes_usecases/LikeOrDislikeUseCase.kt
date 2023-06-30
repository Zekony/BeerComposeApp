package com.example.beercompapp.domain.use_cases.likes_usecases

import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.relations.UserProductItemLikes
import com.example.beercompapp.data.settings.AppSettings
import com.example.beercompapp.domain.model.User
import com.example.beercompapp.domain.repository.LikesRepository

class LikeOrDislikeUseCase(
    private val appSettings: AppSettings,
    private val repository: LikesRepository
) {
    suspend operator fun invoke(UID: String, likedItems: List<ProductItem>) {
        val user = appSettings.getCurrentUser()?: User()
        if (user.login != "") {
            if (likedItems.map { it.UID }.contains(UID)) {
                repository.removeLike(UID, user.phoneNumber)
            } else {
                repository.addLike(userProductItem = UserProductItemLikes(user.phoneNumber, UID))
            }
        }
    }
}