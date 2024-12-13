package me.lokmvne.core.presentstion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import me.lokmvne.common.navigation.NavToDoListScreen
import me.lokmvne.common.navigation.NavToDoTaskScreen
import me.lokmvne.common.navigation.ToDoNavGraph

fun NavGraphBuilder.toDoNavGraph(navHostController: NavHostController) {
    navigation<ToDoNavGraph>(
        startDestination = NavToDoListScreen,
    ) {
        composable<NavToDoListScreen> {
            TodoListScreen(navHostController)
        }

        composable<NavToDoTaskScreen> {
            val args = it.toRoute<NavToDoTaskScreen>()
            ToDoTaskScreen(navHostController, args.taskId)
        }
    }
}
