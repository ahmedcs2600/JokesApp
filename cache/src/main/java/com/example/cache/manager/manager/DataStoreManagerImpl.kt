package com.example.cache.manager.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManagerImpl constructor(private val appContext: Context) :
    DataStoreManager {

    companion object {
        const val STORE_NAME = "JokesStore"
    }

    private val visitCount = intPreferencesKey("visit_count")

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STORE_NAME)

    override suspend fun increaseVisitCount() {
        appContext.dataStore.edit { settings ->
            val currentCounterValue = (settings[visitCount] ?: 0) + 1
            settings[visitCount] = currentCounterValue
        }
    }

    override suspend fun retrieveVisitCount(): Int {
         return appContext.dataStore.data.map { preferences ->
            preferences[visitCount] ?: 0
        }.first()
    }
}