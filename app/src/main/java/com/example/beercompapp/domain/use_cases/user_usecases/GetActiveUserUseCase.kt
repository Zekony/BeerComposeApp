package com.example.beercompapp.domain.use_cases.user_usecases

import com.example.beercompapp.data.settings.AppSettings
import com.example.beercompapp.domain.model.User

class GetActiveUserUseCase(
    private val appSettings: AppSettings
) {
    suspend operator fun invoke(): User {
        return appSettings.getCurrentUser()
    }
}