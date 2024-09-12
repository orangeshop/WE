package com.data.model.response

data class ResponseCouple(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val code: String
    )
}