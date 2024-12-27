package me.lokmvne.core.presentation.navigation

import androidx.navigation.NavHostController
import me.lokmvne.common.navigation.NavToDoListScreen
import me.lokmvne.common.navigation.NavToDoTaskScreen

class Screens(navHostController: NavHostController) {
    val todoList: () -> Unit = {
        navHostController.navigate(NavToDoListScreen) {
            popUpTo(NavToDoListScreen) {
                inclusive = true
            }
        }
    }

    val todoTask: (Int) -> Unit = { taskId ->
        navHostController.navigate(NavToDoTaskScreen(taskId))
    }
}