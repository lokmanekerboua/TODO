package me.lokmvne.core.data.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class DateTypeConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): LocalDate? {
        return value?.let {
            LocalDate.ofEpochDay(value)
        }
    }

    @TypeConverter
    fun dateToTimeStamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}

class TimeTypeConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): LocalTime? {
        return value?.let {
            LocalTime.ofSecondOfDay(it)
        }
    }

    @TypeConverter
    fun timeToTimeStamp(time: LocalTime?): Long? {
        return time?.toSecondOfDay()?.toLong()
    }

}