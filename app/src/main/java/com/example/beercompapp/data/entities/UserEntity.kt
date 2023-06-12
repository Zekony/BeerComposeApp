package com.example.beercompapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.beercompapp.common.Constants.USER_TABLE_NAME
import com.example.beercompapp.domain.model.User

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val phoneNumber: String = "",
    val login: String = "",
    val password: String = ""
) {
    fun toUser(): User {
        return User(
            phoneNumber = phoneNumber,
            login = login,
            password = password,
            role = User.Role.Customer
        )
    }
}