package com.example.beercompapp.domain.repository

import com.example.beercompapp.data.entities.relations.UserProductItemLikes
import com.example.beercompapp.data.entities.relations.UserWithProducts
import kotlinx.coroutines.flow.Flow

interface LikesRepository {

    fun getUserLikes(number: String): Flow<UserWithProducts>

    suspend fun addLike(userProductItem: UserProductItemLikes)

    suspend fun removeLike(UID: String, number: String)

    suspend fun isItemLiked(UID: String): Boolean
}