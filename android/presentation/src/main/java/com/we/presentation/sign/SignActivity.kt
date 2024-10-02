package com.we.presentation.sign

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.we.presentation.R
import com.we.presentation.base.BaseActivity
import com.we.presentation.component.ShareData
import com.we.presentation.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding>(R.layout.activity_sign) {
    private lateinit var navController: NavController


    override fun init() {
        setNavGraph()
    }

    private fun setNavGraph() {
        val type = intent.getBooleanExtra("type", true)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_sign) as NavHostFragment
        navController = navHostFragment.navController
        navController.navInflater.inflate(R.navigation.sign_nav_graph).apply {
            val id = when (ShareData.transferType) {
                true -> {
                    if (type) {
                        R.id.signInFragment
                    } else {
                        R.id.signUpFragment
                    }
                }

                else -> {
                    R.id.signInFragment
                }
            }
            setStartDestination(id)
            navController.setGraph(this, null)
        }
    }

}