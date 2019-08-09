package com.alandvg.movies_app_test_involves.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    fun dateTimeToDateFormatLocalDefault(date: String): String {
        val dateFormat = SimpleDateFormat()
        dateFormat.applyLocalizedPattern("yyyy-MM-dd")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val time = dateFormat.parse(date).time
        return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(time)
    }

}