package com.example.beercompapp.presentation.authorization_screen.authorization

import com.example.beercompapp.presentation.authorization_screen.RegistrationInputFields

data class AuthScreenUiState (
    val registrationInputFields: RegistrationInputFields = RegistrationInputFields(),
    val isUserActive: Boolean = false,
)