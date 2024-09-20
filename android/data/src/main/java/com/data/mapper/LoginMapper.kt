package com.data.mapper

import com.data.model.request.RequestLogin
import com.we.model.LoginParam

fun LoginParam.toModel(): RequestLogin {
    return RequestLogin(
        email = "d104test1027@gmail.com",
        password = "1234"
    )
}