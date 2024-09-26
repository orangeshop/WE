package com.we.presentation.util

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

fun requestPermission(){
    TedPermission.create()
        .setPermissionListener(object : PermissionListener{
            override fun onPermissionGranted() {
                //권한 허용

            }
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                //권한 거부
            }
        })
        .setDeniedMessage("권한을 허용하지 않으면 앱을 사용할 수 없습니다.")
        .setRationaleMessage("앱을 사용하려면, 접근 권한이 필요합니다.")
        .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
        .check()
}