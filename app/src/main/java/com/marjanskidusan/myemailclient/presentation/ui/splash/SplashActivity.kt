package com.marjanskidusan.myemailclient.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        setupSplashStateObserver()
    }

    private fun setupSplashStateObserver() {
        splashActivityViewModel.splashStateLiveData.observe(this) {
            it?.let { splashState ->
                when (splashState) {
                    is SplashActivityViewModel.SplashState.NoInternetConnection -> showError(getString(R.string.noInternet))
                    is SplashActivityViewModel.SplashState.Success -> {
                        hideProgressIndicator()
                        goToEmailsScreen()
                    }
                    is SplashActivityViewModel.SplashState.Error -> {
                        hideProgressIndicator()
                        showError(splashState.errorMessage)
                    }
                }
            }
        }
    }
    
    private fun hideProgressIndicator() {
        binding.progressIndicator.visibility = View.GONE
    }

    private fun goToEmailsScreen() {
        val intent = Intent(this, EmailsActivity::class.java)
        startActivity(intent)
        finish()
    }
}