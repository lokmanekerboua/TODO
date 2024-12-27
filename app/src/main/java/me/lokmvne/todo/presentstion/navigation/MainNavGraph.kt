package me.lokmvne.todo.presentstion.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.lokmvne.common.navigation.ToDoNavGraph
import me.lokmvne.core.presentation.navigation.Screens
import me.lokmvne.core.presentation.navigation.toDoNavGraph

@Composable
fun MainNavGraph(navHostController: NavHostController) {
    val screen = remember(navHostController) {
        Screens(navHostController)
    }
    NavHost(
        navHostController,
        startDestination = ToDoNavGraph,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        toDoNavGraph(screen)
    }
}