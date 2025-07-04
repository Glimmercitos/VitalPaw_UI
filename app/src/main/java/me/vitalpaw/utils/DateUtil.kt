package me.vitalpaw.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")

        outputFormat.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}


