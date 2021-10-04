package com.example.halotelmanewakala

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreference(context: Context) {
    //1
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "mode_preference"
    )

    //2
    suspend fun saveAutoMode(isAutoMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_MODE_KEY] = isAutoMode
        }
    }

    //3
    val autoMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val aMode = preferences[AUTO_MODE_KEY] ?: false
            aMode
        }


    //4
    companion object {
        private val AUTO_MODE_KEY = preferencesKey<Boolean>("auto_mode")
    }

}