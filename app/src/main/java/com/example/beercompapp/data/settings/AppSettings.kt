package com.example.beercompapp.data.settings

import com.example.beercompapp.domain.model.User

interface AppSettings {

    suspend fun getCurrentUser(): User?

    suspend fun setCurrentUser(user: User)
}