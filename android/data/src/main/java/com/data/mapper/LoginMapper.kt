package com.data.mapper

import com.data.model.request.RequestLogin
import com.we.model.LoginParam

fun LoginParam.toModel(): RequestLogin {
    return RequestLogin(
        email = "yh2@gmail.com",
        password = "1234"
    )
}