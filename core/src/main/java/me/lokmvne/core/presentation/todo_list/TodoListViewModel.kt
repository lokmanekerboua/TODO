package me.lokmvne.core.presentation.todo_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.use_cases.ToDoUseCases
import me.lokmvne.core.domain.utils.OrderType
import me.lokmvne.core.domain.utils.ToDoOrder
import me.lokmvne.core.utils.SnackBarAction
import me.lokmvne.core.utils.SnackBarController
import me.lokmvne.core.utils.SnackBarEvent
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val useCases: ToDoUseCases,
) : ViewModel() {
    private var recentlyDeletedTask: ToDoTask? = null
    val snackBarHostState = SnackbarHostState()

    var searchQuery by mutableStateOf("")


    var tasksState by mutableStateOf(TasksState())
        private set

    private var getTasksJob: Job? = null

    init {
        getAllTasks(ToDoOrder.TriggerTime(OrderType.Descending))
    }

    fun onEvent(event: TasksListEvents) {
        when (event) {
//--------------------------------Ordering Tasks Event----------------------------
            is TasksListEvents.Order -> {
                if (tasksState.tasksOrder::class == event.taskOrder::class &&
                    tasksState.tasksOrder.orderType == event.taskOrder.orderType
                ) {
                    return
                }
                getAllTasks(event.taskOrder)
            }
//--------------------------------Search Tasks Event----------------------------
            is TasksListEvents.SearchTask -> {
                searchDatabase(event.searchQuery)
            }
//--------------------------------Delete One Task Event----------------------------
            is TasksListEvents.DeleteTask -> {
                deleteTask(event.toDoTask)
            }
//--------------------------------Delete all Tasks Event----------------------------
            TasksListEvents.DeleteAllTasks -> {
                deleteAllTasks()
            }
//----------------------------Restore Task-------------------------------------------
            TasksListEvents.RestoreTask -> {
                restoreTask(recentlyDeletedTask)
            }
//----------------------------Show Hide App Bar DropDown----------------------------
            TasksListEvents.ExpandTopAppBarDropdown -> {
                tasksState = tasksState.copy(
                    isTopAppBarDropdownExpanded = true
                )
            }

            TasksListEvents.HideTopAppBarDropdown -> {
                tasksState = tasksState.copy(
                    isTopAppBarDropdownExpanded = false
                )
            }
//--------------------------------Show Hide Delete Dialog----------------------------
            TasksListEvents.ShowDeleteDialog -> {
                tasksState = tasksState.copy(
                    isDeleteDialogVisible = true
                )
            }

            TasksListEvents.HideDeleteDialog -> {
                tasksState = tasksState.copy(
                    isDeleteDialogVisible = false
                )
            }
//--------------------------------Show Hide Ordering Section----------------------------
            TasksListEvents.ShowOrderingSection -> {
                tasksState = tasksState.copy(
                    isOrderingSectionVisible = !tasksState.isOrderingSectionVisible
                )
            }
        }
    }

    private fun getAllTasks(taskOrder: ToDoOrder) {
        //getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            useCases.getAllTasksUseCase(taskOrder)
                .collect { tasks ->
                    tasksState = tasksState.copy(
                        todoTasks = tasks,
                        tasksOrder = taskOrder
                    )
                }
        }
    }

    private fun deleteTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            useCases.deleteTaskUseCase(toDoTask)
            recentlyDeletedTask = toDoTask
            SnackBarController.sendEvent(
                event = SnackBarEvent(
                    message = "${toDoTask.title} has been deleted",
                    action = SnackBarAction(
                        name = "Undo",
                        action = {
                            restoreTask(toDoTask)
                        }
                    )
                )
            )
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch {
            useCases.deleteAllTasksUseCase()
        }
    }

    private fun searchDatabase(searchQuery: String) {
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            //delay(200)
            if (searchQuery.isEmpty()) {
                getAllTasks(tasksState.tasksOrder)
                return@launch
            }
            useCases.searchDatabaseUseCase(searchQuery)
                .collect { tasks ->
                    tasksState = tasksState.copy(
                        todoTasks = tasks,
                    )
                }
        }
    }

    private fun restoreTask(task: ToDoTask?) {
        viewModelScope.launch {
            useCases.addTaskUseCase(
                recentlyDeletedTask?.copy(
                    version = recentlyDeletedTask?.version?.plus(1) ?: 0
                )
                    ?: return@launch
            )
            recentlyDeletedTask = null
        }
    }
}