package com.data.mapper

import com.data.model.request.RequestSignUp
import com.we.model.SignUpParam

fun SignUpParam.toModel(): RequestSignUp {
    return RequestSignUp(
        email = "${this.email}@${this.emailName}",
        password = this.password,
        nickname = this.nickname,
        pin = this.easyPassword.joinToString("")
    )
}