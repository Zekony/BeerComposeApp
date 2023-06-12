package com.example.beercompapp.presentation.authorization_screen.registration.utils

data class RegistrationScreenUiState (
    val registrationInputFields: RegistrationInputFields = RegistrationInputFields(),
    val isButtonActive: Boolean = false,
    val errors: RegistrationFieldsErrors = RegistrationFieldsErrors()
)
data class RegistrationFieldsErrors(
    val numberError: String = "",
    val loginError: String = "",
    val passwordError: String = "",
    val repeatPasswordError: String = ""
)