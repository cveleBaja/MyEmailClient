package com.marjanskidusan.myemailclient.presentation.ui.splash

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
                withContext(Dispatchers.Main) {
                    _splashStateLiveData.value = SplashState.NoInternetConnection
                }
                return@launch
            }

            val request = LoginRequestDto("user@gmail.com", "user")
            when (val result = authRepository.login(request)) {
                is Result.Success -> {
                    withContext(Dispatchers.Main) {
                        _splashStateLiveData.value = SplashState.Success
                    }
                }
                is Result.Error -> {
                    withContext(Dispatchers.Main) {
                        _splashStateLiveData.value = SplashState.Error(result.message!!)
                    }
                }
            }
        }
    }
}