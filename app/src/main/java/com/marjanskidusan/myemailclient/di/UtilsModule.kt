package com.marjanskidusan.myemailclient.di

import android.content.Context
import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManager
import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManagerImpl
import com.marjanskidusan.myemailclient.utils.network.NetworkUtils
import com.marjanskidusan.myemailclient.utils.network.NetworkUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun providesNetworkUtils(@ApplicationContext context: Context): NetworkUtils =
        NetworkUtilsImpl(context)

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManagerImpl(context)
}