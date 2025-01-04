package me.lokmvne.core.presentation.todo_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.use_cases.ToDoUseCases
import me.lokmvne.core.utils.AlarmController
import javax.inject.Inject

@HiltViewModel
class ToDoTaskViewModel @Inject constructor(
    private val useCases: ToDoUseCases,
    private val alarmController: AlarmController
) : ViewModel() {
    var singleTaskState by mutableStateOf(SingleTaskState())

    fun onEvent(event: TaskEvents) {
        when (event) {
            TaskEvents.AddTask -> {
                addTask()
            }

            is TaskEvents.UpdateTask -> {
                updateTask(event.taskId)
            }

            is TaskEvents.DeleteTask -> {}
            is TaskEvents.GetSelectedTask -> {
                getSelectedTask(event.taskId)
            }
        }
    }


    private fun addTask() {
        viewModelScope.launch {
            val addedTaskId = useCases.addTaskUseCase(
                ToDoTask(
                    title = singleTaskState.title,
                    description = singleTaskState.description,
                    priority = singleTaskState.priority,
                    illustration = singleTaskState.illustration,
                    taskColor = singleTaskState.taskColor,
                    date = singleTaskState.date,
                    time = singleTaskState.time
                )
            )
            useCases.getSelectedTaskUseCase(addedTaskId).collect {
                alarmController.setAlarmClock(it)
            }
        }
    }

    private fun getSelectedTask(taskId: Long) {
        if (taskId != -1L) {
            viewModelScope.launch {
                useCases.getSelectedTaskUseCase(taskId)
                    .catch {}
                    .collect {
                        singleTaskState = singleTaskState.copy(
                            title = it.title,
                            description = it.description,
                            priority = it.priority,
                            illustration = it.illustration,
                            taskColor = it.taskColor,
                            date = it.date,
                            time = it.time
                        )
                    }
            }
        }
    }


    private fun updateTask(taskId: Long) {
        viewModelScope.launch {
            val task = ToDoTask(
                id = taskId,
                title = singleTaskState.title,
                description = singleTaskState.description,
                priority = singleTaskState.priority,
                illustration = singleTaskState.illustration,
                taskColor = singleTaskState.taskColor,
                date = singleTaskState.date,
                time = singleTaskState.time
            )
            useCases.updateTaskUseCase(task)
            alarmController.setAlarmClock(task)
        }
    }
}