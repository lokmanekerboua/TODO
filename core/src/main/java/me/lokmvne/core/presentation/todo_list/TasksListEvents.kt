package me.lokmvne.core.presentation.todo_list

import me.lokmvne.core.domain.model.ToDoTask
import me.lokmvne.core.domain.utils.ToDoOrder

sealed class TasksListEvents {
    data class Order(val taskOrder: ToDoOrder) : TasksListEvents()
    data class DeleteTask(val toDoTask: ToDoTask) : TasksListEvents()
    data class SearchTask(val searchQuery: String) : TasksListEvents()
    data object DeleteAllTasks : TasksListEvents()
    data object RestoreTask : TasksListEvents()
    data object ExpandTopAppBarDropdown : TasksListEvents()
    data object HideTopAppBarDropdown : TasksListEvents()
    data object ShowDeleteDialog : TasksListEvents()
    data object HideDeleteDialog : TasksListEvents()
    data object ShowOrderingSection : TasksListEvents()
}