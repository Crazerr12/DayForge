package com.example.dayforge.presentation.ui.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateFormatterUtil {
    companion object {
        fun formatDateWithDayOfWeekToYearNumberMonth(
            dateStart: LocalDate,
            timeStart: LocalTime?,
        ): String {
            val localDateTime = LocalDateTime.now()
            val startDateTime =
                if (timeStart != null) dateStart.atTime(timeStart) else dateStart.atStartOfDay()
            val pattern =
                if (localDateTime.year == dateStart.year) "EEE, dd MMM" else "EEE, dd MMM yyyy"

            val dateFormatter = DateTimeFormatter.ofPattern(
                "$pattern ${timeStart.let { "HH:mm" }}",
                Locale.getDefault()
            )

            return startDateTime.format(dateFormatter)
        }
    }
}