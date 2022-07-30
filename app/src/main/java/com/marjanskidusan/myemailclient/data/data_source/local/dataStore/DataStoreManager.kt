package com.marjanskidusan.myemailclient.data.data_source.local.dataStore

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun write(key: String, value: String)
    fun read(key: String): Flow<String>
    suspend fun clearDataStore()
}