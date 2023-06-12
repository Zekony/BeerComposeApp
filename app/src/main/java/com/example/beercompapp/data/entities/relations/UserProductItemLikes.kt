package com.example.beercompapp.data.entities.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import com.example.beercompapp.common.Constants.LIKES_TABLE_NAME
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.UserEntity

@Entity(
    tableName = LIKES_TABLE_NAME,
    primaryKeys = ["phoneNumber", "UID"],
    indices = [Index("phoneNumber"), Index("UID")],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("phoneNumber"),
            childColumns = arrayOf("phoneNumber"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = ProductItem::class,
            parentColumns = arrayOf("UID"),
            childColumns = arrayOf("UID"),
            onDelete = CASCADE
        )
    ]
)

data class UserProductItemLikes(
    val phoneNumber: String = "",
    val UID: String = ""
)
