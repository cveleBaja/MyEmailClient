package com.marjanskidusan.myemailclient.data.data_source.remote

import com.marjanskidusan.myemailclient.data.model.LoginRequestDto
import com.marjanskidusan.myemailclient.data.model.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("secured/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}