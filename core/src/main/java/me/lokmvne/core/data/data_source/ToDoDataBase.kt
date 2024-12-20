package me.lokmvne.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import me.lokmvne.core.domain.model.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDataBase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}