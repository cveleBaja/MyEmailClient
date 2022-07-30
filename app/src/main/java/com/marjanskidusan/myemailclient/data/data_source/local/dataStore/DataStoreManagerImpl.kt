package com.marjanskidusan.myemailclient.data.data_source.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
) : DataStoreManager {

    companion object {
        const val accessToken = "accessToken"
    }

    private val Context.datastore by preferencesDataStore("MyEmailClientDataStore")
    private val dataStore = context.datastore

    override suspend fun write(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override fun read(key: String): Flow<String> = dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey(key)].orEmpty()
        }

    override suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }
}