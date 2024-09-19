package com.data.mapper

import com.data.model.request.RequestSignUp
import com.we.model.SignUpParam

fun SignUpParam.toModel(): RequestSignUp {
    return RequestSignUp(
        email = "d104test45@gmail.com",
        password = this.password,
        nickname = this.nickname,
        pin = this.easyPassword.joinToString("")
    )
}