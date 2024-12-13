package me.lokmvne.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.lokmvne.core.utils.Constants.Companion.TODO_TASK_TABLE

@Entity(tableName = TODO_TASK_TABLE)
//@TypeConverters(DateTypeConverter::class)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val date: String
)
