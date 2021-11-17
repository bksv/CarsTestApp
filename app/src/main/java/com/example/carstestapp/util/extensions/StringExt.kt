package com.example.carstestapp.util.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.carstestapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun String.getColorFromCarState(context: Context): Int {
    return when (this) {
        "available" -> ContextCompat.getColor(context, R.color.car_available)
        "disabled" -> ContextCompat.getColor(context, R.color.car_disabled)
        else -> ContextCompat.getColor(context, R.color.car_hidden)
    }
}

fun String.humanizeDate(): String {
    return if (this.isBlank().not()) {
        val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00", Locale.ENGLISH)
        val date: Date? = dateFormat.parse(this)
        if (date != null) {
            val formatter: DateFormat = SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.ENGLISH)
            formatter.format(date)
        } else {
            "Date could not be parsed"
        }
    } else {
        "String is blank"
    }
}

