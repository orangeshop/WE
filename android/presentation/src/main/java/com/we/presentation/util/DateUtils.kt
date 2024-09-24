package com.we.presentation.util

import java.time.LocalDate

fun LocalDate.toYearMonth() : Pair<String,String>{
    val date = this.toString().split("-")
    return Pair(date[0], date[1])

}