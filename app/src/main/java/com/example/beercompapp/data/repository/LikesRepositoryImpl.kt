package com.example.beercompapp.data.repository

import com.example.beercompapp.data.dao.LikesDao
import com.example.beercompapp.data.entities.relations.UserProductItemLikes
import com.example.beercompapp.data.entities.relations.UserWithProducts
import com.example.beercompapp.domain.repository.LikesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikesRepositoryImpl @Inject constructor(
    private val dao: LikesDao
) : LikesRepository {

    override fun getUserLikes(number: String): Flow<UserWithProducts> {
        return dao.getUserLikes(number)
    }

    override suspend fun addLike(userProductItem: UserProductItemLikes) {
        dao.addLike(userProductItem)
    }

    override suspend fun removeLike(UID: String, number: String) {
        dao.removeLike(UID, number)
    }

    override suspend fun isItemLiked(UID: String): Boolean {
        return dao.isItemLiked(UID)
    }


}