package me.lokmvne.core.presentation.todo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.lokmvne.common.data_store.ToDoPreferences
import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.use_cases.ToDoUseCases
import me.lokmvne.core.utils.SnackBarAction
import me.lokmvne.core.utils.SnackBarController
import me.lokmvne.core.utils.SnackBarEvent
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val useCases: ToDoUseCases,
    private val toDoPreferences: ToDoPreferences
) : ViewModel() {
    private var recentlyDeletedTask: ToDoTask? = null

    var searchQuery by mutableStateOf("")

    var tasksState by mutableStateOf(TasksState())
        private set

    private var getTasksJob: Job? = null

    init {
        getHighPriorityTasks()
        getAllTasks()
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
                viewModelScope.launch {
                    toDoPreferences.setToDoOrderType(event.taskOrder)
                    getAllTasks()
                }
            }
//--------------------------------Search Tasks Event----------------------------
            is TasksListEvents.SearchTask -> {
                searchDatabase(event.searchQuery)
            }
//--------------------------------Delete One Task Event----------------------------
            is TasksListEvents.DeleteTask -> {
                deleteTask(event.toDoTask)
            }
//----------------------------Restore Task-------------------------------------------
            TasksListEvents.RestoreTask -> {
                restoreTask()
            }

            TasksListEvents.DeleteAllTasks -> {
                viewModelScope.launch {
                    tasksState = tasksState.copy(
                        isDeleteDialogVisible = false
                    )
                    useCases.deleteAllTasksUseCase()
                }
            }

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

            TasksListEvents.HideDeleteDialog -> {
                tasksState = tasksState.copy(
                    isDeleteDialogVisible = false
                )
            }


            TasksListEvents.ShowDeleteDialog -> {
                tasksState = tasksState.copy(
                    isDeleteDialogVisible = true
                )
            }

            TasksListEvents.ShowOrderingSection -> {
                tasksState = tasksState.copy(
                    isSearchBoxVisible = false,
                    isOrderingSectionVisible = !tasksState.isOrderingSectionVisible
                )
            }

            TasksListEvents.ShowSearchBox -> {
                tasksState = tasksState.copy(
                    isOrderingSectionVisible = false,
                    isSearchBoxVisible = !tasksState.isSearchBoxVisible
                )
            }
        }
    }

    private fun getAllTasks(/*taskOrder: ToDoOrder*/) {
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            toDoPreferences.readToDoOrderType().collect {
                useCases.getAllTasksUseCase(it)
                    .collect { tasks ->
                        tasksState = tasksState.copy(
                            todoTasks = tasks,
                            tasksOrder = it
                        )
                    }
            }
        }
    }

    private fun getHighPriorityTasks() {
        viewModelScope.launch {
            useCases.getHighPriorityTasks().collect {
                tasksState = tasksState.copy(
                    highPriorityTasks = it
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
                            restoreTask()
                        }
                    )
                )
            )
        }
    }

    private fun searchDatabase(searchQuery: String) {
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            //delay(200)
            if (searchQuery.isEmpty()) {
                getAllTasks()
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

    private fun restoreTask() {
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