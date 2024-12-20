package me.lokmvne.core.domain.use_cases

import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.repository.ToDoRepository

class DeleteTaskUseCase(private val repository: ToDoRepository) {
    suspend operator fun invoke(toDoTask: ToDoTask) {
        repository.deleteTask(toDoTask)
    }
}