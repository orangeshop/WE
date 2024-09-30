package com.we.presentation.home

import android.webkit.WebView
import android.webkit.WebViewClient
import com.we.presentation.R
import com.we.presentation.base.BaseActivity
import com.we.presentation.databinding.ActivityInvitationDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class InvitationDetailActivity :
    BaseActivity<ActivityInvitationDetailBinding>(R.layout.activity_invitation_detail) {
    override fun init() {
        initWebView()
    }

    private fun initWebView() {
        val number = intent.getIntExtra("param" , 0)
        binding.webView.apply {
            clearCache(true)
            settings.javaScriptEnabled = true
            getSettings().setLoadWithOverviewMode(true);
            settings.domStorageEnabled = true
            loadUrl("https://j11d104.p.ssafy.io/invitation/storage/$number")
        }
    }
}