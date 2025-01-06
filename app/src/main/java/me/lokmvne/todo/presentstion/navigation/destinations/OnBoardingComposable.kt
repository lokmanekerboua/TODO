package me.lokmvne.todo.presentstion.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.lokmvne.common.navigation.OnBoardingScreen
import me.lokmvne.todo.presentstion.onBoarding.OnBoarding

fun NavGraphBuilder.onBoardingComposable(navigate: () -> Unit) {
    composable<OnBoardingScreen> {
        OnBoarding(navigate)
    }
}