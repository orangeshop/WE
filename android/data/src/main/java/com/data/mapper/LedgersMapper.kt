package com.data.mapper

import com.data.model.response.ResponseGetLedgers
import com.data.model.response.ResponsePostLedgers
import com.we.model.LedgersData


fun ResponsePostLedgers.Data.toModel(): LedgersData {
    return LedgersData(
        url = LedgerId.toString()
    )
}

fun ResponseGetLedgers.Data.LedgerInfo.toModel(): LedgersData {
    return LedgersData(
        url = qrCodeUrl ?: ""
    )
}