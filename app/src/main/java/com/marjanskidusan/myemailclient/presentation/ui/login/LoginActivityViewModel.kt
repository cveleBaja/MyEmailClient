package com.marjanskidusan.myemailclient.presentation.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marjanskidusan.myemailclient.data.model.LoginRequestDto
import com.marjanskidusan.myemailclient.domain.repository.AuthRepository
import com.marjanskidusan.myemailclient.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    var emailLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")
    private var _loginSuccessLiveData = MutableLiveData<Boolean?>()
    val loginSuccessLiveData get() = _loginSuccessLiveData
    private var _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData get() = _errorMessageLiveData

    fun login() {
        if (!isInputValid()) return

        viewModelScope.launch(Dispatchers.IO) {
            val request = configureLoginBody()

            when (val result = authRepository.login(request)) {
                is Result.Success -> _loginSuccessLiveData.value = true
                is Result.Error -> _errorMessageLiveData.value = result.message

            }
        }
    }

    private fun isInputValid(): Boolean =
        !emailLiveData.value.isNullOrEmpty() && !passwordLiveData.value.isNullOrEmpty()

    private fun configureLoginBody(): LoginRequestDto =
        LoginRequestDto(email = emailLiveData.value ?: "", password = passwordLiveData.value ?: "")

}