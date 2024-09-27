package com.data.model.response

data class ResponseSignIn(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val memberInfo: MemberInfo,
        val tokens: Tokens
    ) {
        data class MemberInfo(
            val email: String,
            val id: Int,
            val imageUrl: Any,
            val leaved: Boolean,
            val leavedDate: Any,
            val nickname: String,
            val regDate: String,
            val coupleJoined: Boolean
        )

        data class Tokens(
            val accessToken: String,
            val refreshToken: String
        )
    }
}