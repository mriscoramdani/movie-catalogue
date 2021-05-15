package com.riscodev.moviecat.utils

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