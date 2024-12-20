package me.lokmvne.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.lokmvne.core.data.utils.DateTypeConverter
import me.lokmvne.core.data.utils.Priority
import me.lokmvne.core.data.utils.TimeTypeConverter
import me.lokmvne.core.utils.Constants.Companion.TODO_TASK_TABLE
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = TODO_TASK_TABLE)
@TypeConverters(DateTypeConverter::class, TimeTypeConverter::class)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val date: LocalDate?,
    val time: LocalTime?
)