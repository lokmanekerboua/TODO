package me.lokmvne.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import me.lokmvne.core.data.utils.DateTypeConverter
import me.lokmvne.core.data.utils.Priority
import me.lokmvne.core.data.utils.TimeTypeConverter
import me.lokmvne.core.utils.Constants.Companion.TODO_TASK_TABLE
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = TODO_TASK_TABLE)
@TypeConverters(DateTypeConverter::class, TimeTypeConverter::class)
@Parcelize
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    var version: Int = 0,
    val description: String,
    val priority: Priority,
    val illustration: Int,
    val taskColor: Long,
    val date: LocalDate,
    val time: LocalTime
) : Parcelable