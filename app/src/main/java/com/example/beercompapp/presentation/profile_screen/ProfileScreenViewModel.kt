package com.example.beercompapp.presentation.profile_screen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.R
import com.example.beercompapp.common.InvalidLoginException
import com.example.beercompapp.common.PasswordException
import com.example.beercompapp.data.entities.UserEntity
import com.example.beercompapp.domain.model.User
import com.example.beercompapp.domain.use_cases.user_usecases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state.asStateFlow()

    init {
        getActiveUser()
    }

    private fun getActiveUser() {
        viewModelScope.launch {
            val activeUser = userUseCases.getActiveUserUseCase()
            _state.update {
                it.copy(
                    //так как нам нужен старый пароль чтобы менять его на новый, то нам нужен юзер из датабазы, а не из датастора
                    user = if (activeUser.phoneNumber.isNotEmpty()) {
                        userUseCases.getUserByNumberUseCase(activeUser.phoneNumber, null)
                    } else {
                        activeUser
                    }
                )
            }
        }
    }

    fun getLogin(inputText: String) {
        _state.update { currentState ->
            currentState.copy(
                inputFields = currentState.inputFields.copy(
                    newLogin = inputText
                ),
                errors = ProfileFieldsErrors()
            )
        }
        enableChangeButton()
    }

    fun getPassword(inputText: String) {
        _state.update { currentState ->
            currentState.copy(
                inputFields = currentState.inputFields.copy(
                    password = inputText
                ),
                errors = ProfileFieldsErrors()
            )
        }
        enableChangeButton()
    }

    fun getRepeatPassword(inputText: String) {
        _state.update { currentState ->
            currentState.copy(
                inputFields = currentState.inputFields.copy(
                    repeatPassword = inputText
                ),
                errors = ProfileFieldsErrors()
            )
        }
        enableChangeButton()
    }

    private fun enableChangeButton() {
        when (_state.value.showChangeFields) {
            ShowChangeFields.ChangePassword -> {
                _state.update {
                    it.copy(
                        isButtonActive = it.inputFields.password.isNotEmpty() &&
                                it.inputFields.password.isNotEmpty()
                    )
                }
            }
            ShowChangeFields.ChangeLogin -> {
                _state.update {
                    it.copy(
                        isButtonActive = it.inputFields.newLogin.isNotEmpty()
                    )
                }
            }
            else -> {}
        }
    }

    fun onSaveChanges(context: Context) {
        viewModelScope.launch {
            try {
                // так как мы используем одну и ту же кнопку для изменения логина и пароля, то приходится передавать больше значений с условиями
                userUseCases.updateUserUseCase(
                    newUser = UserEntity(
                        phoneNumber = _state.value.user.phoneNumber,
                        login = if (_state.value.inputFields.newLogin == "") {
                            _state.value.user.login
                        } else {
                            _state.value.inputFields.newLogin
                        },
                        password = if (_state.value.inputFields.password == "") {
                            _state.value.user.password
                        } else {
                            _state.value.inputFields.password
                        }
                    ),
                    oldUser = _state.value.user,
                    change = _state.value.showChangeFields,
                    repeatPassword = _state.value.inputFields.repeatPassword
                )
                // показать тоаст когда удачно поменяли логин или пароль
                when (_state.value.showChangeFields) {
                    ShowChangeFields.ChangePassword -> Toast.makeText(
                        context,
                        context.getString(R.string.password_is_changed),
                        Toast.LENGTH_SHORT
                    ).show()
                    ShowChangeFields.ChangeLogin -> Toast.makeText(
                        context,
                        context.getString(R.string.login_is_changed),
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> {}
                }
                getActiveUser()
            } catch (e: InvalidLoginException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: PasswordException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showChangeField(fields: ShowChangeFields) {
        if (fields == _state.value.showChangeFields) {
            _state.update {
                it.copy(
                    showChangeFields = ShowChangeFields.None,
                    inputFields = ProfileInputFields()
                )
            }
        } else {
            _state.update {
                it.copy(
                    showChangeFields = fields,
                    inputFields = ProfileInputFields()
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userUseCases.setCurrentUserUseCase(User())
            _state.update {
                it.copy(
                    user = User()
                )
            }
        }
    }
}