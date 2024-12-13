package me.lokmvne.core.domain

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.data.models.ToDoTask
import me.lokmvne.core.repository.TodoRepository
import javax.inject.Inject

class ToDoUseCases @Inject constructor(
    private val todoReporitory: TodoRepository
) {
    fun getAllTasks(): Flow<List<ToDoTask>> {
        return todoReporitory.getAllTasks()
    }

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return todoReporitory.getSelectedTask(taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask) {
        todoReporitory.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        todoReporitory.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        todoReporitory.deleteTask(toDoTask)
    }

    suspend fun deleteAllTasks() {
        todoReporitory.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return todoReporitory.searchDatabase(searchQuery)
    }

    fun sortByLowPriority(): Flow<List<ToDoTask>> {
        return todoReporitory.sortByLowPriority()
    }

    fun sortByHighPriority(): Flow<List<ToDoTask>> {
        return todoReporitory.sortByHighPriority()
    }
}