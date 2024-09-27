package com.we.presentation.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.InvitationAdapter
import com.we.presentation.databinding.FragmentInvitationBinding
import com.we.presentation.home.model.InvitationUiState
import com.we.presentation.home.viewmodel.InvitationViewModel
import com.we.presentation.util.addCustomItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class InvitationFragment : BaseFragment<FragmentInvitationBinding>(R.layout.fragment_invitation) {
    private lateinit var invitationAdapter: InvitationAdapter

    private val invitationViewModel: InvitationViewModel by viewModels()

    override fun initView() {
        initInvitationAdapter()
        initClickEventListener()
        observeInvitationUiState()
    }


    private fun initInvitationAdapter() {
        invitationAdapter = InvitationAdapter()
        binding.vpInvitation.apply {
            adapter = invitationAdapter
            offscreenPageLimit = 1
            this.addCustomItemDecoration()
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.shareVisible = position != invitationAdapter.itemCount - 1
                }
            })
        }


    }

    private fun observeInvitationUiState() {
        invitationViewModel.invitationUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is InvitationUiState.InvitationSuccess -> {
                        invitationAdapter.submitList(it.data)
                    }

                    is InvitationUiState.InvitationError -> {

                    }

                    else -> {

                    }
                }

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
            invitationAdapter.setItemClickListener {
                val url = "https://j11d104.p.ssafy.io/be/v1/invitation/formal/$it"
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                })
            }
            btnShare.setOnClickListener{
                setShareEvent()
            }
        }
    }

    private fun setShareEvent(){
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "야호",
                imageUrl = "이미지",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            )
        )

        // 카카오톡 설치여부 확인
        if (ShareClient.instance.isKakaoTalkSharingAvailable(requireActivity())) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(requireActivity(),defaultFeed) { sharingResult, error ->
                if (error != null) {

                }
                else if (sharingResult != null) {

                    startActivity(sharingResult.intent)

                }
            }
        } else {

            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)


            try {
                KakaoCustomTabsClient.openWithDefault(requireActivity(), sharerUrl)
            } catch(e: UnsupportedOperationException) {
                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
            // ex) 다음, 네이버 등
            try {
                KakaoCustomTabsClient.open(requireActivity(), sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }
}