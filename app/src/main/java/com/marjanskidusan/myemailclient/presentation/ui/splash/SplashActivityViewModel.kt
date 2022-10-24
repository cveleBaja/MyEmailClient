package com.marjanskidusan.myemailclient.presentation.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marjanskidusan.myemailclient.data.model.LoginRequestDto
import com.marjanskidusan.myemailclient.domain.repository.AuthRepository
import com.marjanskidusan.myemailclient.utils.Result
import com.marjanskidusan.myemailclient.utils.network.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val authRepository: AuthRepository,
) :
    ViewModel() {

    companion object {
        private const val delayInMS = 5000L
    }

    sealed class SplashState {
        object NoInternetConnection : SplashState()
        object Success : SplashState()
        class Error(val errorMessage: String): SplashState()
    }

    private var _splashStateLiveData = MutableLiveData<SplashState?>()
    val splashStateLiveData get() = _splashStateLiveData

    init {
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        val hasInternet = networkUtils.isInternetConnected()

        viewModelScope.launch(Dispatchers.IO) {
            if (!hasInternet) {
                delay(delayInMS)
                setSplashState(SplashState.NoInternetConnection)
                return@launch
            }

            val isUserLoggedIn = authRepository.isUserLoggedIn()
            if (isUserLoggedIn) {
                setSplashState(SplashState.Success)
                return@launch
            }

            login()
        }
    }

    private suspend fun login() {
        val request = LoginRequestDto("user@gmail.com", "user")

        when (val result = authRepository.login(request)) {
            is Result.Success -> {
                setSplashState(SplashState.Success)
            }
            is Result.Error -> {
                setSplashState(SplashState.Error(result.message!!))
            }
        }
    }

    private suspend fun setSplashState(state: SplashState) {
        withContext(Dispatchers.Main) {
            _splashStateLiveData.value = state
        }
    }
}