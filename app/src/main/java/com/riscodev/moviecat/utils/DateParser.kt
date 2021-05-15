package com.riscodev.moviecat.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    @SuppressLint("SimpleDateFormat")
    fun parse(str_date: String, pattern: String): String {
        try {
            val date = SimpleDateFormat(pattern, Locale.getDefault()).parse(str_date)
            date?.apply {
                return SimpleDateFormat("dd MMM yyy").format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str_date
    }
}