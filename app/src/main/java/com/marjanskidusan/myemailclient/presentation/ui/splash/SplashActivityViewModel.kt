package com.marjanskidusan.myemailclient.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marjanskidusan.myemailclient.utils.network.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val networkUtils: NetworkUtils) :
    ViewModel() {

    companion object {
        private const val delayInMS = 5000L
    }

    private var _hasInternetLiveData = MutableLiveData<Boolean?>()
    val hasInternetLiveData get() = _hasInternetLiveData

    init {
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        val hasInternet = networkUtils.isInternetConnected()

        viewModelScope.launch(Dispatchers.Default) {
            delay(delayInMS)
            _hasInternetLiveData.value = hasInternet
        }
    }
}