package com.example.beercompapp.presentation.profile_screen

import com.example.beercompapp.domain.model.User

data class ProfileScreenState(
    val user: User = User(),
    val isButtonActive: Boolean = false,
    val inputFields: ProfileInputFields = ProfileInputFields(),
    val errors: ProfileFieldsErrors = ProfileFieldsErrors(),
    val showChangeFields: ShowChangeFields = ShowChangeFields.None
)

enum class ShowChangeFields {
    ChangeLogin, ChangePassword, None
}

data class ProfileInputFields(
    val password: String = "",
    val repeatPassword: String = "",
    val newLogin: String = ""
)

data class ProfileFieldsErrors(
    val loginError: String = "",
    val passwordError: String = ""
)


