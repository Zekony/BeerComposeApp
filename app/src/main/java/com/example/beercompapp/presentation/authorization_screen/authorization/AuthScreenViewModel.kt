package com.example.beercompapp.presentation.authorization_screen.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.domain.repository.UserRepository
import com.example.beercompapp.domain.use_cases.user_use_cases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val useCases: UserUseCases
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

    fun getPassword(inputText: String) {
        _user.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    password = inputText.toCharArray()
                )
            )
        }
    }

    fun authorize() {
        viewModelScope.launch {

        }
    }
}