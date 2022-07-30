package com.marjanskidusan.myemailclient.di

import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManager
import com.marjanskidusan.myemailclient.data.data_source.remote.ApiService
import com.marjanskidusan.myemailclient.data.repository.AuthRepositoryImpl
import com.marjanskidusan.myemailclient.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        apiService: ApiService,
        dataStoreManager: DataStoreManager
    ): AuthRepository =
        AuthRepositoryImpl(apiService, dataStoreManager)
}