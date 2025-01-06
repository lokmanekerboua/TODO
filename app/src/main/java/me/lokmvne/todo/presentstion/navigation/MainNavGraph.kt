package me.lokmvne.todo.presentstion.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.lokmvne.common.navigation.SplashScreen
import me.lokmvne.core.presentation.navigation.Screens
import me.lokmvne.core.presentation.navigation.toDoNavGraph
import me.lokmvne.todo.presentstion.navigation.destinations.onBoardingComposable
import me.lokmvne.todo.presentstion.navigation.destinations.signInScreenComposable
import me.lokmvne.todo.presentstion.navigation.destinations.splashScreenComposable

@Composable
fun MainNavGraph(navHostController: NavHostController, context: Context) {
    val screen = remember(navHostController) {
        Screens(navHostController)
    }
    NavHost(
        navHostController,
        startDestination = SplashScreen,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        signInScreenComposable(screen.todoList, context)
        onBoardingComposable(screen.toSignIn)
        splashScreenComposable(screen)
        toDoNavGraph(screen)
    }
}