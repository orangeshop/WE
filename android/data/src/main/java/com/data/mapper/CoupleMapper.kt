package com.data.mapper

import com.data.model.request.RequestCouple
import com.we.model.CoupleParam

fun CoupleParam.toModel(): RequestCouple {
    return RequestCouple(
        code = this.code
    )
}