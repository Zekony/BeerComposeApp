package com.example.beercompapp.data.settings

import androidx.datastore.preferences.core.Preferences
import com.example.beercompapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppSettings {

    fun addListener(): Flow<Preferences>
    suspend fun getCurrentUser(): User

    suspend fun setCurrentUser(user: User)
}