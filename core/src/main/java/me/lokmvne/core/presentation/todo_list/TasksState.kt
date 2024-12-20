package me.lokmvne.core.presentation.todo_list

import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.utils.OrderType
import me.lokmvne.core.domain.utils.ToDoOrder

data class TasksState(
    val todoTasks: List<ToDoTask> = emptyList(),
    val tasksOrder: ToDoOrder = ToDoOrder.TriggerTime(OrderType.Descending),
    val isTopAppBarDropdownExpanded: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isOrderingSectionVisible: Boolean = false
)
