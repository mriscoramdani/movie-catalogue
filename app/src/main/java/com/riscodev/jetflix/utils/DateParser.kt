package com.riscodev.jetflix.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    fun parse(str_date: String, pattern: String): String {
        try {
            val date = SimpleDateFormat(pattern, Locale.getDefault()).parse(str_date)
            return SimpleDateFormat("dd MMM yyy").format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str_date
    }
}