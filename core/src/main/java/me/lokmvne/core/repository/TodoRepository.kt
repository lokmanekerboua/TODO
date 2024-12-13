package me.lokmvne.core.repository

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.data.models.ToDoDao
import me.lokmvne.core.data.models.ToDoTask
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: ToDoDao
) {
    fun getAllTasks(): Flow<List<ToDoTask>> {
        return todoDao.getAllTasks()
    }

    suspend fun addTask(toDoTask: ToDoTask) {
        todoDao.addTask(toDoTask)
    }

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return todoDao.getSelectedTask(taskId)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        todoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        todoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTasks() {
        todoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return todoDao.searchDatabase(searchQuery)
    }

    fun sortByLowPriority(): Flow<List<ToDoTask>> {
        return todoDao.sortByLowPriority()
    }

    fun sortByHighPriority(): Flow<List<ToDoTask>> {
        return todoDao.sortByHighPriority()
    }
}