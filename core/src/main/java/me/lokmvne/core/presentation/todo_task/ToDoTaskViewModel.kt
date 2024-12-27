package me.lokmvne.core.presentation.todo_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.lokmvne.core.data.utils.Priority
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.use_cases.ToDoUseCases
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ToDoTaskViewModel @Inject constructor(
    private val useCases: ToDoUseCases
) : ViewModel() {
    var isPriorityExpended by mutableStateOf(false)
    var isIllustExpended by mutableStateOf(false)
    var isColorExpended by mutableStateOf(false)
    var isDatePickerShowed by mutableStateOf(false)
    var isTimePickerShowed by mutableStateOf(false)

    var illustText by mutableStateOf(illustrationsList.first().first)
    var colorText by mutableStateOf(colorsList.first().first)

    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.LOW)
    var illustration = mutableStateOf(illustrationsList.first().second)
    var taskColor = mutableStateOf(colorsList.first().second)
    val date = mutableStateOf<LocalDate?>(null)
    val time = mutableStateOf<LocalTime?>(null)


    fun onEvent(event: TaskEvents) {
        when (event) {
            TaskEvents.AddTask -> {
                addTask()
            }

            is TaskEvents.UpdateTask -> {
                updateTask(event.taskId)
            }

            is TaskEvents.DeleteTask -> {}
        }
    }


    private fun addTask() {
        viewModelScope.launch {
            useCases.addTaskUseCase(
                ToDoTask(
                    title = title.value,
                    description = description.value,
                    priority = priority.value,
                    illustration = illustration.value,
                    taskColor = taskColor.value,
                    date = date.value,
                    time = time.value
                )
            )
        }
    }

    fun getSelectedTask(taskId: Int) {
        if (taskId != -1) {
            viewModelScope.launch {
                useCases.getSelectedTaskUseCase(taskId)
                    .catch {}
                    .collect {
                        title.value = it.title
                        description.value = it.description
                        priority.value = it.priority
                        illustration.value = it.illustration
                        taskColor.value = it.taskColor
                        date.value = it.date
                        time.value = it.time

                        colorText =
                            colorsList.find { color -> color.second == it.taskColor }!!.first
                        illustText =
                            illustrationsList.find { illus -> illus.second == it.illustration }!!.first
                    }
            }
        }
    }


    private fun updateTask(taskId: Int) {
        viewModelScope.launch {
            useCases.updateTaskUseCase(
                ToDoTask(
                    id = taskId,
                    title = title.value,
                    description = description.value,
                    priority = priority.value,
                    illustration = illustration.value,
                    taskColor = taskColor.value,
                    date = date.value,
                    time = time.value
                )
            )
        }
    }
}