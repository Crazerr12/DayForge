package com.example.data_local.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class Converters {
    @TypeConverter
    fun fromLocalDate(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(value) }

    @TypeConverter
    fun toLocalDate(date: LocalDate?): Long? = date?.toEpochDay()

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): Long? =
        if (localTime == null) {
            null
        } else {
            localTime.hour * 60L * 60L + localTime.minute * 60L + localTime.second
        }

    @TypeConverter
    fun toLocalTime(value: Long?): LocalTime? {
        if (value == null) {
            return null
        }
        val seconds: Long = TimeUnit.SECONDS.convert(value, TimeUnit.MILLISECONDS)
        val hour = (seconds / 3600).toInt()
        val minute = (seconds % 3600 / 60).toInt()
        val second = (seconds % 60).toInt()
        return LocalTime.of(hour, minute, second)
    }
}