package com.example.beercompapp.presentation.authorization_screen.authorization.utils

data class AuthScreenUiState(
    val loginInputFields: LoginInputFields = LoginInputFields(),
    val isButtonActive: Boolean = false,
    val errors: LoginFieldsErrors = LoginFieldsErrors()
)

data class LoginFieldsErrors(
    val numberError: String = "",
    val passwordError: String = ""
)