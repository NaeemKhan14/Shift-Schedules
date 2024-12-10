package com.example.shiftschedules.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension for DataStore
val Context.dataStore by preferencesDataStore("user_preferences")

object UserPreferencesKeys {
    val LANGUAGE_KEY = stringPreferencesKey("language")
}

class UserPreferences(private val context: Context) {
    private val dataStore = context.dataStore

    val selectedLanguage: Flow<String> = dataStore.data.map { preferences ->
        preferences[UserPreferencesKeys.LANGUAGE_KEY] ?: "en" // Default to "en"
    }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.LANGUAGE_KEY] = language
        }
    }
}
