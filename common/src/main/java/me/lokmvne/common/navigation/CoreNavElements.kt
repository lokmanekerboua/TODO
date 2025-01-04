package me.lokmvne.common.navigation

import kotlinx.serialization.Serializable

@Serializable
object ToDoNavGraph

@Serializable
object NavToDoListScreen

@Serializable
data class NavToDoTaskScreen(
    val taskId: Long = -1L,
)