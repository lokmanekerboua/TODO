package me.lokmvne.core.repository

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.data.data_source.ToDoDao
import me.lokmvne.core.domain.model.ToDoTask

class ToDoRepositoryImpl(private val dao: ToDoDao) : ToDoRepository {
    override fun getAllTasks(): Flow<List<ToDoTask>> {
        return dao.getAllTasks()
    }

    override suspend fun addTask(toDoTask: ToDoTask): Long {
        return dao.addTask(toDoTask)
    }

    override fun getSelectedTask(taskId: Long): Flow<ToDoTask> {
        return dao.getSelectedTask(taskId)
    }

    override fun getHighPriorityTasks(): Flow<List<ToDoTask>> {
        return dao.getHighPriorityTasks()
    }

    override fun getNearTasks(startDate: Long, endDate: Long): Flow<List<ToDoTask>> {
        return dao.getNearTasks(startDate, endDate)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        return dao.updateTask(toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        dao.deleteTask(toDoTask)
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }

    override fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return dao.searchDatabase(searchQuery)
    }
}