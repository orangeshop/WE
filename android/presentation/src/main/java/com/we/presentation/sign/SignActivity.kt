package com.we.presentation.sign

import android.content.Intent
import android.net.Uri
import com.we.presentation.R
import com.we.presentation.base.BaseActivity
import com.we.presentation.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding>(R.layout.activity_sign) {

    override fun init() {

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        val data: Uri? = intent.data
        data?.let {
            val legId = data.getQueryParameter("id")

        }
    }
}