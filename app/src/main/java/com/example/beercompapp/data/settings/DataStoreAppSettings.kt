package com.example.beercompapp.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.beercompapp.common.Constants.DATA_STORE_NAME
import com.example.beercompapp.common.Constants.USER_PREFERENCES_KEY
import com.example.beercompapp.domain.model.User
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreAppSettings @Inject constructor(
    @ApplicationContext val appContext: Context
) : AppSettings {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        DATA_STORE_NAME
    )

    private val userKey = stringPreferencesKey(USER_PREFERENCES_KEY)

    override suspend fun getCurrentUser(): User? {
        val json = appContext.dataStore.data.firstOrNull()
        return if (json != null) {
            GsonBuilder().create().fromJson(json[userKey], User::class.java)
        } else {
            User()
        }
    }

    override suspend fun setCurrentUser(user: User) {
        appContext.dataStore.edit {
            it[userKey] = GsonBuilder().create().toJson(user)
        }
    }
}