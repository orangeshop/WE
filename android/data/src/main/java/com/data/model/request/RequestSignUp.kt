package com.data.model.request

data class RequestSignUp(
    val email : String,
    val password : String,
    val nickname : String,
    //간편 비밀번호
)