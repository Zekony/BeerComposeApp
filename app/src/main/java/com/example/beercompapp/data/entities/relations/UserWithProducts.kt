package com.example.beercompapp.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.UserEntity

data class UserWithProducts(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "phoneNumber",
        entityColumn = "UID",
        associateBy = Junction(
            value = UserProductItemLikes::class,
            parentColumn = "phoneNumber",
            entityColumn = "UID"
        )
    )
    val menuItems: List<ProductItem>?
)
