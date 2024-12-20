package me.lokmvne.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.repository.ToDoRepository

class SearchDatabaseUseCase(private val repository: ToDoRepository) {
    operator fun invoke(searchQuery: String): Flow<List<ToDoTask>> {
        return repository.searchDatabase(searchQuery)
    }
}