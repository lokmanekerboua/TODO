package me.lokmvne.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.repository.ToDoRepository

class GetSelectedTaskUseCase(private val repository: ToDoRepository) {
    operator fun invoke(toDoTaskId: Long): Flow<ToDoTask> {
        return repository.getSelectedTask(toDoTaskId)
    }
}