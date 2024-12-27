package me.lokmvne.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.repository.ToDoRepository

class GetNearTasks(private val repository: ToDoRepository) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Flow<List<ToDoTask>> {
        return repository.getNearTasks(startDate, endDate)
    }
}