package me.lokmvne.core.presentstion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.lokmvne.core.data.models.ToDoTask
import me.lokmvne.core.domain.ToDoUseCases
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val useCases: ToDoUseCases
) : ViewModel() {
    private val _todoTasks = MutableStateFlow(emptyList<ToDoTask>())
    val todoTasks = _todoTasks.asStateFlow()

    init {
        getAllTasks()
    }
    fun getAllTasks() {
        viewModelScope.launch {
            useCases.getAllTasks().collect {
                _todoTasks.value = it
            }
        }
    }

    fun deleteTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            useCases.deleteTask(toDoTask)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            useCases.deleteAllTasks()
        }
    }

    fun searchDatabase(searchQuery: String) {
        viewModelScope.launch {
            useCases.searchDatabase(searchQuery).collect {
                _todoTasks.value = it
            }
        }
    }

    fun sortByLowPriority() {
        viewModelScope.launch {
            useCases.sortByLowPriority().collect {
                _todoTasks.value = it
            }
        }
    }

    fun sortByHighPriority() {
        viewModelScope.launch {
            useCases.sortByHighPriority().collect {
                _todoTasks.value = it
            }
        }
    }
}