package com.marjanskidusan.myemailclient.presentation.ui.emails

import android.os.Bundle
import com.marjanskidusan.myemailclient.databinding.ActivityEmailsBinding
import com.marjanskidusan.myemailclient.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailsActivity: BaseActivity() {

    private lateinit var binding: ActivityEmailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}