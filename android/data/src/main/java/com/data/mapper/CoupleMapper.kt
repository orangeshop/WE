package com.data.mapper

import com.data.model.response.ResponseCouples
import com.data.model.response.ResponseCouplesCode
import com.data.model.response.ResponseGetCouples
import com.we.model.CoupleData
import com.we.model.CoupleSuccessData
import com.we.model.GetCoupleData

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

fun ResponseGetCouples.Data.CoupleInfo.toModel(): GetCoupleData{
    return GetCoupleData(
        CoupeInfo = id
    )
}