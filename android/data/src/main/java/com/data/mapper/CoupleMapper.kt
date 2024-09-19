package com.data.mapper

import com.data.model.request.RequestCouple
import com.data.model.response.ResponseCouple
import com.data.model.response.ResponseLogin
import com.we.model.CoupleData

fun ResponseCouple.toEntity(): CoupleData {
    return CoupleData(
        code = this.data.code
    )
}