package com.marjanskidusan.myemailclient.presentation.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.marjanskidusan.myemailclient.databinding.ActivityLoginBinding
import com.marjanskidusan.myemailclient.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        loginActivityViewModel.loginSuccessLiveData.observe(this) {
            it?.let { isSuccessful ->
                if (!isSuccessful) return@observe
                goToHomeScreen()
            }
        }
    }

    private fun goToHomeScreen() {
        // TODO: Implement
    }

    private fun setOnClickListeners() {
        binding.etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginActivityViewModel.emailLiveData.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }
        })

        binding.etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginActivityViewModel.passwordLiveData.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }
        })

        binding.btnLogin.setOnClickListener {
            loginActivityViewModel.login()
        }
    }
}