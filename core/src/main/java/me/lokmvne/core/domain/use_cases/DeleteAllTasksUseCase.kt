package me.lokmvne.core.domain.use_cases

import me.lokmvne.core.repository.ToDoRepository

class DeleteAllTasksUseCase(private val repository: ToDoRepository) {
    suspend operator fun invoke() {
        repository.deleteAllTasks()
    }
}