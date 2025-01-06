package me.lokmvne.todo.presentstion.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.lokmvne.common.navigation.SplashScreen
import me.lokmvne.core.presentation.navigation.Screens
import me.lokmvne.todo.presentstion.splash.SplashScreen

fun NavGraphBuilder.splashScreenComposable(screens: Screens) {
    composable<SplashScreen> {
        SplashScreen(screens)
    }
}