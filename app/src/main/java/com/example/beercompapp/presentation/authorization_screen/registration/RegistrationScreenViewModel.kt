package com.example.beercompapp.presentation.authorization_screen.registration

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.common.InvalidLoginException
import com.example.beercompapp.common.InvalidNumberException
import com.example.beercompapp.common.NumberAlreadyExistsException
import com.example.beercompapp.common.PasswordException
import com.example.beercompapp.domain.use_cases.user_usecases.UserUseCases
import com.example.beercompapp.presentation.authorization_screen.registration.utils.RegistrationFieldsErrors
import com.example.beercompapp.presentation.authorization_screen.registration.utils.RegistrationInputFields
import com.example.beercompapp.presentation.authorization_screen.registration.utils.RegistrationScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _userState = MutableStateFlow(RegistrationScreenUiState())
    val userState = _userState.asStateFlow()

    private val _onSuccessEvent = MutableSharedFlow<Boolean>()
    val onSuccessEvent: SharedFlow<Boolean> = _onSuccessEvent

    fun getNumber(inputText: String) {
        _userState.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    number = inputText
                ),
                errors = RegistrationFieldsErrors(
                    repeatPasswordError = userState.value.errors.repeatPasswordError
                )
            )
        }
        enableRegisterButton()
    }

    fun getLogin(inputText: String) {
        _userState.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    login = inputText
                )
            )
        }
        enableRegisterButton()
    }

    fun getPassword(inputText: String) {
        _userState.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    password = inputText.toCharArray()
                ),
                errors = RegistrationFieldsErrors(numberError = userState.value.errors.numberError)
            )
        }
        enableRegisterButton()
    }

    fun getRepeatPassword(inputText: String) {
        _userState.update { currentState ->
            currentState.copy(
                registrationInputFields = currentState.registrationInputFields.copy(
                    repeatPassword = inputText.toCharArray()
                ),
                errors = RegistrationFieldsErrors(numberError = userState.value.errors.numberError)
            )
        }
        enableRegisterButton()
    }

    private fun enableRegisterButton() {
        _userState.update {
            it.copy(
                isButtonActive = it.registrationInputFields.login.isNotEmpty() &&
                        it.registrationInputFields.password.isNotEmpty() &&
                        it.registrationInputFields.number.isNotEmpty() &&
                        it.registrationInputFields.repeatPassword.isNotEmpty()
            )
        }
    }

    fun createUser(context: Context) {
        viewModelScope.launch {
            try {
                userUseCases.addUserUseCase(
                    inputFields = userState.value.registrationInputFields
                )
                _onSuccessEvent.emit(true)
                _userState.update {
                    it.copy(
                        registrationInputFields = RegistrationInputFields()
                    )
                }
                showToast("Аккаунт создан", context)
            } catch (e: NumberAlreadyExistsException) {
                _userState.update {
                    it.copy(
                        errors = RegistrationFieldsErrors(numberError = e.message ?: "")
                    )
                }
                showToast(userState.value.errors.numberError, context)
            } catch (e: InvalidNumberException) {
                _userState.update {
                    it.copy(
                        errors = RegistrationFieldsErrors(numberError = e.message ?: "")
                    )
                }
                showToast(userState.value.errors.numberError, context)

            } catch (e: PasswordException.RepeatPasswordException) {
                _userState.update {
                    it.copy(
                        errors = RegistrationFieldsErrors(repeatPasswordError = e.message ?: "")
                    )
                }
                showToast(userState.value.errors.repeatPasswordError, context)
            } catch (e: PasswordException.NewPasswordException) {
                _userState.update {
                    it.copy(
                        errors = RegistrationFieldsErrors(passwordError = e.message ?: "")
                    )
                }
                showToast(userState.value.errors.passwordError, context)

            } catch (e: InvalidLoginException) {
                _userState.update {
                    it.copy(
                        errors = RegistrationFieldsErrors(loginError = e.message ?: "")
                    )
                }
                showToast(userState.value.errors.loginError, context)

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