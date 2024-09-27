package com.data.mapper

import com.data.model.request.RequestLogin
import com.we.model.SignParam

fun SignParam.toModel(): RequestLogin {
    return RequestLogin(
        email = "boy@test.com",
        password = "1234"
    )
}