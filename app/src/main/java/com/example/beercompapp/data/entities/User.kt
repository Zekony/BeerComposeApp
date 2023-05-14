package com.example.beercompapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey
    val number: String = "",
    val login: String = "",
    val password: String = ""
)
