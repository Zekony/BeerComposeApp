package com.example.beercompapp.domain.model

data class User(
    val phoneNumber: String = "",
    val login: String = "",
    val password: String = "",
    val role: Role = Role.NoUser
) {
    sealed class Role(val name: String) {
        object NoUser: Role("noUser")
        object Customer: Role("customer")
    }
}