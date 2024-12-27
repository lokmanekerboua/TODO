package me.lokmvne.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.repository.ToDoRepository

class GetHighPriorityTasks(private val repository: ToDoRepository) {
    suspend operator fun invoke(): Flow<List<ToDoTask>> {
        return repository.getHighPriorityTasks()
    }
}