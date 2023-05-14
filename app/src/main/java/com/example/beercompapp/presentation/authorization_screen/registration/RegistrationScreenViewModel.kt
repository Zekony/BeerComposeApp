package com.example.beercompapp.presentation.authorization_screen.registration

import androidx.lifecycle.ViewModel
import com.example.beercompapp.presentation.authorization_screen.authorization.AuthScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _user = MutableStateFlow(AuthScreenUiState())
    val user = _user.asStateFlow()

    fun getLogin(inputText: String) {
        _user.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    login = inputText
                )
            )
        }
    }
    fun getNumber(inputText: String) {
        _user.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    number = inputText
                )
            )
        }
    }
    fun getPassword(inputText: String) {
        _user.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    password = inputText.toCharArray()
                )
            )
        }
    }
    fun getRepeatPassword(inputText: String) {
        _user.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    repeatPassword = inputText.toCharArray()
                )
            )
        }
    }
}