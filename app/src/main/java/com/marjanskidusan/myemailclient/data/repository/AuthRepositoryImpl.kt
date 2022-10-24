package com.marjanskidusan.myemailclient.data.repository

import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManager
import com.marjanskidusan.myemailclient.data.data_source.local.dataStore.DataStoreManagerImpl
import com.marjanskidusan.myemailclient.data.data_source.remote.ApiService
import com.marjanskidusan.myemailclient.data.model.LoginRequestDto
import com.marjanskidusan.myemailclient.domain.repository.AuthRepository
import com.marjanskidusan.myemailclient.utils.Result
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager,
) : AuthRepository {

    override suspend fun login(request: LoginRequestDto): Result<Unit> {
        return try {
            val response = apiService.login(request)
            dataStoreManager.write(key = DataStoreManagerImpl.accessToken, value = response.token)

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        val token = dataStoreManager.read(DataStoreManagerImpl.accessToken).first()

        return token != ""
    }
}