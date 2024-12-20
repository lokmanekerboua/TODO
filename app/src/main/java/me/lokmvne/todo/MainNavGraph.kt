package me.lokmvne.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.lokmvne.common.navigation.ToDoNavGraph
import me.lokmvne.core.presentation.Screens
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
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        toDoNavGraph(screen)
    }
}