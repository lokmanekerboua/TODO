package me.lokmvne.core.presentation.todo_profile

import me.lokmvne.common.data.ToDoUser

data class ProfileState(
    val toDoUser: ToDoUser = ToDoUser(name = "", email = "", photoUrl = ""),
)