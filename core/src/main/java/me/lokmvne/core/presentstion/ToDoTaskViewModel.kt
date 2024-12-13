package me.lokmvne.core.presentstion

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.lokmvne.core.data.models.Priority
import me.lokmvne.core.data.models.ToDoTask
import me.lokmvne.core.domain.ToDoUseCases
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ToDoTaskViewModel @Inject constructor(
    private val useCases: ToDoUseCases
) : ViewModel() {
    private val current: LocalDateTime = LocalDateTime.now()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.LOW)
    val date = mutableStateOf(current.format(formatter))

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            useCases.getSelectedTask(taskId).collect {
                title.value = it.title
                description.value = it.description
                priority.value = it.priority
            }
        }
    }

    fun updateTask() {
        viewModelScope.launch {
            val task = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value,
                date = date.value
            )
            useCases.updateTask(task)
        }
    }

    fun addTask() {
        viewModelScope.launch {
            val task = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value,
                date = date.value
            )

            useCases.addTask(task)
        }
    }
}