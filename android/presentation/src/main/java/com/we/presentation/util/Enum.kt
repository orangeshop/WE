package com.we.presentation.util

import com.we.presentation.R

enum class CalendarType {
    BEFORE,
    TODAY,
    CURRENT,
    AFTER
}

enum class Page(val hideBottomNav: Boolean) {
    HOME(true),
    SCHEDULE(true),
    MY_PAGE(true),
    SCHEDULE_REGISTER(false),

    ;


    companion object {
        fun fromId(id: Int): Page? = entries.find { it.id == id }
    }

    val id: Int
        get() = when (this) {
            HOME -> R.id.fragment_home
            SCHEDULE -> R.id.fragment_schedule
            MY_PAGE -> R.id.fragment_my_page
            SCHEDULE_REGISTER -> R.id.fragment_schedule_register
        }
}
