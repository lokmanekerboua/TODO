package me.lokmvne.core.presentation.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.lokmvne.common.navigation.NavToDoTaskScreen
import me.lokmvne.core.presentation.todo_task.ToDoTaskScreen

fun NavGraphBuilder.taskComposableScreen(
    navigateToListScreen: () -> Unit,
) {
    composable<NavToDoTaskScreen> {
        val args = it.toRoute<NavToDoTaskScreen>()
        ToDoTaskScreen(navigateToListScreen,args.taskId)
    }
}