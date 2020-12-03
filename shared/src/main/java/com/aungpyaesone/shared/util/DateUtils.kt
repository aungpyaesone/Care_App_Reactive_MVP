package com.aungpyaesone.shared.util

import java.util.*

class DateUtils {
    fun getDayAgo(dayAgo:Int): Date{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -dayAgo)
        return calendar.time
    }
}