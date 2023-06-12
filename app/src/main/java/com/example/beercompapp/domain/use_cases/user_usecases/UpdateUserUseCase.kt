package com.example.beercompapp.domain.use_cases.user_usecases

import android.content.Context
import com.example.beercompapp.R
import com.example.beercompapp.common.InvalidLoginException
import com.example.beercompapp.common.PasswordException
import com.example.beercompapp.data.entities.UserEntity
import com.example.beercompapp.domain.model.User
import com.example.beercompapp.domain.repository.UserRepository
import com.example.beercompapp.presentation.profile_screen.ShowChangeFields
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val context: Context
) {
    suspend operator fun invoke(
        newUser: UserEntity,
        oldUser: User,
        change: ShowChangeFields,
        repeatPassword: String
    ) {
        when (change) {
            ShowChangeFields.ChangeLogin -> {
                if (newUser.login.length <= 3) {
                    throw InvalidLoginException(context.getString(R.string.login_is_too_short_exception))
                }
                if (newUser.login == oldUser.login) {
                    throw InvalidLoginException(context.getString(R.string.new_login_is_old_login_exception))
                }
            }
            ShowChangeFields.ChangePassword -> {
                if (newUser.password != repeatPassword) {
                    throw PasswordException.RepeatPasswordException(context.getString(R.string.passwords_should_match_exception))
                }
                if (newUser.password.length <= 3) {
                    throw PasswordException.NewPasswordException(context.getString(R.string.password_is_too_short_exception))
                }
            }
            else -> {}
        }
        repository.updateUser(newUser)
    }
}