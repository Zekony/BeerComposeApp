package com.example.beercompapp.domain.use_cases.user_usecases

import android.content.Context
import com.example.beercompapp.R
import com.example.beercompapp.common.PasswordException
import com.example.beercompapp.common.UserDoesntExistException
import com.example.beercompapp.domain.model.User
import com.example.beercompapp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByNumberUseCase @Inject constructor(
    private val repository: UserRepository,
    private val context: Context
) {
    suspend operator fun invoke(number: String, password: String?): User {
        val user = repository.getUserByNumber(number)
            ?: throw UserDoesntExistException(context.getString(R.string.user_doesnt_exist_exception))
        if (password != null && user.password != password) {
            throw PasswordException.OldPasswordException(context.getString(R.string.wrong_password_exception))
        }
        return user.toUser()
    }
}