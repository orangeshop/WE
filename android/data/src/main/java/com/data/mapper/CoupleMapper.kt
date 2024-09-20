package com.data.mapper

import com.data.model.response.ResponseCouples
import com.data.model.response.ResponseCouplesCode
import com.we.model.CoupleData
import com.we.model.CoupleSuccessData

fun ResponseCouplesCode.toEntity(): CoupleData {
    return CoupleData(
        code = this.data.code
    )
}

fun ResponseCouples.toModel(): CoupleSuccessData {
    return CoupleSuccessData(
        coupleId = this.data.coupleId
    )
}