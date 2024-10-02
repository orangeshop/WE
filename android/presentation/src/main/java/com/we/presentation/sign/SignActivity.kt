package com.we.presentation.sign

import android.content.Intent
import com.we.presentation.R
import com.we.presentation.base.BaseActivity
import com.we.presentation.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding>(R.layout.activity_sign) {

    override fun init() {
        initData()
    }

    private fun initData() {
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            if (uri != null) {
                val id = uri.getQueryParameter("id")
                Timber.tag("이체하기").d("$id")
            }
        }
    }
}