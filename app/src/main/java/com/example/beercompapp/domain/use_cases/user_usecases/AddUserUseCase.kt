package com.example.beercompapp.domain.use_cases.user_usecases

import android.content.Context
import com.example.beercompapp.R
import com.example.beercompapp.common.InvalidLoginException
import com.example.beercompapp.common.InvalidNumberException
import com.example.beercompapp.common.NumberAlreadyExistsException
import com.example.beercompapp.common.PasswordException
import com.example.beercompapp.data.entities.UserEntity
import com.example.beercompapp.domain.repository.UserRepository
import com.example.beercompapp.presentation.authorization_screen.registration.utils.RegistrationInputFields
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val context: Context
) {
    suspend operator fun invoke(inputFields: RegistrationInputFields) {
        if (!inputFields.password.contentEquals(inputFields.repeatPassword)) {
            throw PasswordException.RepeatPasswordException(context.getString(R.string.passwords_should_match_exception))
        }
        if (inputFields.password.size <= 3){
            throw PasswordException.NewPasswordException(context.getString(R.string.password_is_too_short_exception))
        }
        if (inputFields.login.length <= 3){
            throw InvalidLoginException(context.getString(R.string.login_is_too_short_exception))
        }
        if (inputFields.number.length <= 3){
            throw InvalidNumberException(context.getString(R.string.number_is_too_short_exception))
        }
        val userEntity = repository.getUserByNumber(inputFields.number)
        userEntity?.let {
            throw NumberAlreadyExistsException(context.getString(R.string.user_already_exists_exception))
        }
        repository.addUser(
            user = UserEntity(
                phoneNumber = inputFields.number,
                login = inputFields.login,
                password = inputFields.password.joinToString("")
            )
        )
    }
}