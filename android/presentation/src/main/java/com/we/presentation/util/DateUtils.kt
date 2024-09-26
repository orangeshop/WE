package com.we.presentation.util

import timber.log.Timber
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.toYearMonth(): Pair<String, String> {
    val date = this.toString().split("-")
    return Pair(date[0], date[1])
}


fun String.convertIsoToLocalDate(): LocalDate? {
    return runCatching {
        OffsetDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }.map { offsetDateTime ->
        offsetDateTime.toLocalDate()
    }.onFailure {
        Timber.d("에러 처리 ${it.printStackTrace()}")
    }.getOrNull()
}