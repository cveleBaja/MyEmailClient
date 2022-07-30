package com.marjanskidusan.myemailclient.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.marjanskidusan.myemailclient.R
import com.marjanskidusan.myemailclient.databinding.ActivitySplashBinding
import com.marjanskidusan.myemailclient.presentation.base.BaseActivity
import com.marjanskidusan.myemailclient.presentation.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity: BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashActivityViewModel: SplashActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
    }

    private fun setupObservers() {
        splashActivityViewModel.hasInternetLiveData.observe(this) {
            it?.let { hasInternet ->
                if (!hasInternet) {
                    showError(getString(R.string.noInternet))
                    return@observe
                }
                goToLoginScreen()
            }
        }
    }

    private fun goToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}