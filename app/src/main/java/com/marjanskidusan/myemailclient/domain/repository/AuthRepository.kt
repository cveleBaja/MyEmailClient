package com.marjanskidusan.myemailclient.domain.repository

import com.marjanskidusan.myemailclient.data.model.LoginRequestDto
import com.marjanskidusan.myemailclient.utils.Result

interface AuthRepository {

    suspend fun login(request: LoginRequestDto): Result<Unit>
    suspend fun isUserLoggedIn(): Boolean
}