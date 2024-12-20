package me.lokmvne.common.navigation

import kotlinx.serialization.Serializable

@Serializable
object ToDoNavGraph

@Serializable
object NavToDoListScreen

@Serializable
data class NavToDoTaskScreen(
    val taskId: Int = -1,
)

enum class ToDoActions {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}