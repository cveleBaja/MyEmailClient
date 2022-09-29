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

    private var _noInternetLiveData = MutableLiveData<Boolean?>()
    val noInternetLiveData get() = _noInternetLiveData
    private var _loginSuccessLiveData = MutableLiveData<Boolean?>()
    val loginSuccessLiveData get() = _loginSuccessLiveData

    init {
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        val hasInternet = networkUtils.isInternetConnected()

        viewModelScope.launch(Dispatchers.IO) {
            if (!hasInternet) {
                delay(delayInMS)
                _noInternetLiveData.value = false
                return@launch
            }

            val request = LoginRequestDto("user@gmail.com", "user")
            when (val result = authRepository.login(request)) {
                is Result.Success -> {
                    _loginSuccessLiveData.value = true
                }
                is Result.Error -> {
                    Log.i("TAG", "checkInternetConnection: ${result.message}")
                }
            }
        }
    }
}