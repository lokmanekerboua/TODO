package me.lokmvne.core.presentation.todo_list

import androidx.compose.material3.SnackbarHostState
import me.lokmvne.common.utils.OrderType
import me.lokmvne.common.utils.ToDoOrder
import me.lokmvne.core.domain.model.ToDoTask

data class TasksState(
    val todoTasks: List<ToDoTask> = emptyList(),
    val highPriorityTasks: List<ToDoTask> = emptyList(),
    val nearTasks: List<ToDoTask> = emptyList(),
    val tasksOrder: ToDoOrder = ToDoOrder.Priority(OrderType.Descending),
    val isTopAppBarDropdownExpanded: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isOrderingSectionVisible: Boolean = false,
    val isSearchBoxVisible: Boolean = false,
    val snackBarState: SnackbarHostState = SnackbarHostState(),
)