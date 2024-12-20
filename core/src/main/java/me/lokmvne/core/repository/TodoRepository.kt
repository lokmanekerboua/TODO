package me.lokmvne.core.repository

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.domain.model.ToDoTask

interface ToDoRepository {
    fun getAllTasks(): Flow<List<ToDoTask>>

    suspend fun addTask(toDoTask: ToDoTask)

    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    suspend fun updateTask(toDoTask: ToDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask)

    suspend fun deleteAllTasks()

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>
}