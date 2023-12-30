package com.dherediat97.randomuserapp.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: String): String? {
    val patternSource = "yyyy-mm-dd"
    val patternDest = "dd/mm/yyyy"
    val simpleDateFormatSource = SimpleDateFormat(patternSource, Locale.getDefault())
    val simpleDateFormatDest = SimpleDateFormat(patternDest, Locale.getDefault())

    val dateDest = simpleDateFormatSource.parse(date)

    return dateDest?.let { simpleDateFormatDest.format(it) }
}