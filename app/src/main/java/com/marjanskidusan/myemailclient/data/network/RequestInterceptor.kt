package com.marjanskidusan.myemailclient.data.network

import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManager
import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManagerImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(private val dataStoreManager: DataStoreManager) :
    Interceptor {

    companion object {
        const val authorizationKey = "Authorization"
        const val bearer = "Bearer "
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return runBlocking {
            val result = dataStoreManager.read(DataStoreManagerImpl.accessToken).first()

            val newRequest = if (result.isNotEmpty()) {
                request.newBuilder().addHeader(authorizationKey, "$bearer $result")
                    .build()
            } else {
                request
            }
            chain.proceed(newRequest)
        }
    }
}