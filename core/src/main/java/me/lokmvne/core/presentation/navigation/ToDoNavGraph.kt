package me.lokmvne.core.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import me.lokmvne.common.navigation.NavToDoListScreen
import me.lokmvne.common.navigation.ToDoNavGraph
import me.lokmvne.core.presentation.navigation.destinations.listComposableScreen
import me.lokmvne.core.presentation.navigation.destinations.profileComposableScreen
import me.lokmvne.core.presentation.navigation.destinations.taskComposableScreen


fun NavGraphBuilder.toDoNavGraph(
    screen: Screens,
) {
    navigation<ToDoNavGraph>(
        startDestination = NavToDoListScreen
    ) {
        profileComposableScreen(screen.todoList, screen.toSignIn)
        listComposableScreen(screen.todoTask, screen.goToProfile)
        taskComposableScreen(screen.todoList)
    }
}