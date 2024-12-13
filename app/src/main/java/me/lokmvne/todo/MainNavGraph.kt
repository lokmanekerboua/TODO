package me.lokmvne.todo

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.lokmvne.common.navigation.ToDoNavGraph
import me.lokmvne.core.presentstion.toDoNavGraph

@Composable
fun MainNavGraph(navHostController: NavHostController) {
    NavHost(
        navHostController,
        startDestination = ToDoNavGraph,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        toDoNavGraph(navHostController)
    }
}