package com.marjanskidusan.myemailclient.di

import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManager
import com.marjanskidusan.myemailclient.data.data_source.remote.ApiService
import com.marjanskidusan.myemailclient.data.network.RequestInterceptor
import com.marjanskidusan.myemailclient.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_INTERVAL = 30L

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesRequestInterceptor(dataStoreManager: DataStoreManager) =
        RequestInterceptor(dataStoreManager)

    @Singleton
    @Provides
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(requestInterceptor)
            .readTimeout(TIMEOUT_INTERVAL, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_INTERVAL, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_INTERVAL, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}