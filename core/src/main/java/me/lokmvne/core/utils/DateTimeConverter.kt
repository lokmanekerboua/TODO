package me.lokmvne.core.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun <T> dateTimeToString(value: T, patterns: String): String? {
    return when (value) {
        is LocalDate -> value.format(DateTimeFormatter.ofPattern(patterns))
        is LocalTime -> value.format(DateTimeFormatter.ofPattern(patterns))
        else -> throw IllegalArgumentException()
    }
}