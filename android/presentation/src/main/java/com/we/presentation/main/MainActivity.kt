package com.we.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.we.presentation.R
import com.we.presentation.base.BaseActivity
import com.we.presentation.databinding.ActivityMainBinding
import com.we.presentation.main.viewmodel.MainViewModel
import com.we.presentation.util.Page
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()

    override fun init() {
        setNavGraph()
        initBottomNavigation()
        setBottomNavHide()
        getFcmToken()
    }

    private fun setNavGraph() {
        val type = Intent().getBooleanExtra("type", true)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navController.navInflater.inflate(R.navigation.nav_graph).apply {
            val id = if (type) {
                R.id.homeFragment
            } else {
                R.id.guestFragment
            }
            setStartDestination(id)
            navController.setGraph(this, null)
        }
    }

    private fun initBottomNavigation() { //바텀 네비게이션 초기화

        binding.bottomNavMain.setupWithNavController(navController)
    }

    /** 바텀 네비게이션 숨기는 기능 */
    private fun setBottomNavHide() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val page = Page.fromId(destination.id)
            binding.bottomVisibility = page?.hideBottomNav == true
        }
    }


    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.d("Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Timber.d("FCM : ${token}")
            mainViewModel.postToken(token)
        })
    }
}
