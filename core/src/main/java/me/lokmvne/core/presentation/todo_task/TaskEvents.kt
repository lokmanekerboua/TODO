package me.lokmvne.core.presentation.todo_task

import me.lokmvne.core.domain.model.ToDoTask

sealed class TaskEvents {
    data object AddTask : TaskEvents()
    data class DeleteTask(val task: ToDoTask) : TaskEvents()
    data class UpdateTask(val taskId: Long) : TaskEvents()
    data class GetSelectedTask(val taskId: Long) : TaskEvents()
}