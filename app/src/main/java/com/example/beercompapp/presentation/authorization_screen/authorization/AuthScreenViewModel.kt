package com.example.beercompapp.presentation.authorization_screen.authorization

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.common.PasswordException
import com.example.beercompapp.common.UserDoesntExistException
import com.example.beercompapp.domain.use_cases.user_usecases.UserUseCases
import com.example.beercompapp.presentation.authorization_screen.authorization.utils.AuthScreenUiState
import com.example.beercompapp.presentation.authorization_screen.authorization.utils.LoginFieldsErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val useCases: UserUseCases
) : ViewModel() {

    private val _userState = MutableStateFlow(AuthScreenUiState())
    val userState = _userState.asStateFlow()

    private val _onSuccessEvent = MutableSharedFlow<Boolean>()
    val onSuccessEvent: SharedFlow<Boolean> = _onSuccessEvent


    fun getNumber(inputText: String) {
        _userState.update { currentState ->
            currentState.copy(
                loginInputFields = currentState.loginInputFields.copy(
                    number = inputText
                ),
                errors = LoginFieldsErrors()
            )
        }
        enableLoginButton()
    }

    fun getPassword(inputText: String) {
        _userState.update { currentState ->
            currentState.copy(
                loginInputFields = currentState.loginInputFields.copy(
                    password = inputText.toCharArray()
                ),
                errors = LoginFieldsErrors()
            )
        }
        enableLoginButton()
    }

    private fun enableLoginButton() {
        _userState.update {
            it.copy(
                isButtonActive = it.loginInputFields.number.isNotEmpty() &&
                        it.loginInputFields.password.isNotEmpty()
            )
        }
    }

    fun authorize(context: Context) {
        viewModelScope.launch {
            _onSuccessEvent.emit(false)
            _userState.update {
                it.copy(
                    errors = userState.value.errors.copy(
                        passwordError = "",
                        numberError = ""
                    )
                )
            }
            try {
                val user = useCases.getUserByNumberUseCase(
                    _userState.value.loginInputFields.number,
                    _userState.value.loginInputFields.password.joinToString("")
                )
                _onSuccessEvent.emit(true)
                Log.d(
                    "AuthScreenVM",
                    "Authorization - login is ${_userState.value.loginInputFields.number}, password is ${
                        _userState.value.loginInputFields.password.joinToString("")
                    }"
                )
                useCases.setCurrentUserUseCase(user)
            } catch (e: UserDoesntExistException) {
                _userState.emit(
                    userState.value.copy(
                        errors = userState.value.errors.copy(
                            numberError = e.message ?: ""
                        )
                    )
                )
                showToast(userState.value.errors.numberError, context)
            } catch (e: PasswordException) {
                _userState.emit(
                    userState.value.copy(
                        errors = userState.value.errors.copy(
                            passwordError = e.message ?: ""
                        )
                    )
                )
                showToast(userState.value.errors.passwordError, context)
            }
        }
    }

    private fun showToast(text: String, context: Context) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}