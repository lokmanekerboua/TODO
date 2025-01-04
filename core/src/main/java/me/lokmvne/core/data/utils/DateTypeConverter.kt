package me.lokmvne.core.data.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class DateTypeConverter {
    @TypeConverter
    fun fromTimeStamp(value: Float): LocalDate {
        return value.let {
            LocalDate.ofEpochDay(value.toLong())
        }
    }

    @TypeConverter
    fun dateToTimeStamp(date: LocalDate): Float {
        return date.toEpochDay().toFloat()
    }
}

class TimeTypeConverter {
    @TypeConverter
    fun fromTimeStamp(value: Float): LocalTime {
        return value.let {
            LocalTime.ofSecondOfDay(it.toLong())
        }
    }

    @TypeConverter
    fun timeToTimeStamp(time: LocalTime): Float {
        return time.toSecondOfDay().toFloat()
    }

}