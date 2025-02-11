package me.lokmvne.core.presentation.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.lokmvne.common.navigation.NavToDoListScreen
import me.lokmvne.core.presentation.todo_list.TodoListScreen

fun NavGraphBuilder.listComposableScreen(
    navigateToTaskScreen: (Long) -> Unit,
    goToProfile: () -> Unit
) {
    composable<NavToDoListScreen> {
        TodoListScreen(navigateToTaskScreen,goToProfile)
    }
}