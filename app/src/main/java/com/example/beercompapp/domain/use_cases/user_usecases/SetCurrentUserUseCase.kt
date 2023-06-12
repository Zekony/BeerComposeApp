package com.example.beercompapp.domain.use_cases.user_usecases

import com.example.beercompapp.data.settings.AppSettings
import com.example.beercompapp.domain.model.User

class SetCurrentUserUseCase(
    private val appSettings: AppSettings
) {
    suspend operator fun invoke(user: User) {
        appSettings.setCurrentUser(user)
    }
}