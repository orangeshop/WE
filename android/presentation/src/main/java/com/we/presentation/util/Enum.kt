package com.we.presentation.util

import com.we.presentation.R

enum class CalendarType {
    BEFORE,
    TODAY,
    CURRENT,
    AFTER
}

/**
 * true -> visible
 * false -> gone
 */
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
            HOME -> R.id.homeFragment
            SCHEDULE -> R.id.scheduleFragment
            MY_PAGE -> R.id.myPageFragment
            SCHEDULE_REGISTER -> R.id.scheduleRegisterFragment
        }
}
