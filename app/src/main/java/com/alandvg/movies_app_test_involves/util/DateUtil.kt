package com.alandvg.movies_app_test_involves.util

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    private val TAG = this@DateUtil.javaClass.simpleName
    fun dateTimeToDateFormatLocalDefault(date: String): String {
        return try {
            val dateFormat = SimpleDateFormat()
            dateFormat.applyLocalizedPattern("yyyy-MM-dd")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val time = dateFormat.parse(date).time
            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(time)
        }catch (e:Exception){
            Log.e(TAG, e.message.toString())
            ""
        }
    }

}