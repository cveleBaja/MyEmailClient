package com.marjanskidusan.myemailclient.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.marjanskidusan.myemailclient.R
import com.marjanskidusan.myemailclient.databinding.ActivitySplashBinding
import com.marjanskidusan.myemailclient.presentation.base.BaseActivity
import com.marjanskidusan.myemailclient.presentation.ui.emails.EmailsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashActivityViewModel: SplashActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNoInternetObserver()
        setupLoginSuccessObserver()
    }

    private fun setupLoginSuccessObserver() {
        splashActivityViewModel.loginSuccessLiveData.observe(this) {
            it?.let { loginSuccess ->
                if (!loginSuccess) {
                    return@observe
                }

                goToEmailsScreen()
            }
        }
    }

    private fun setupNoInternetObserver() {
        splashActivityViewModel.noInternetLiveData.observe(this) {
            it?.let { noInternet ->
                showError(getString(R.string.noInternet))
            }
        }
    }

    private fun goToEmailsScreen() {
        val intent = Intent(this, EmailsActivity::class.java)
        startActivity(intent)
        finish()
    }
}