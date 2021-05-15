package com.riscodev.jetflix.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun emptyCheck(value: String): String {
        if (value.isEmpty()) {
            return "..."
        }
        return value
    }

    fun countCheck(count: Int): Boolean {
        return count > 0
    }
}