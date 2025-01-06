package me.lokmvne.core.presentation.navigation

import androidx.navigation.NavHostController
import me.lokmvne.common.navigation.NavToDoListScreen
import me.lokmvne.common.navigation.NavToDoProfileScreen
import me.lokmvne.common.navigation.NavToDoTaskScreen
import me.lokmvne.common.navigation.OnBoardingScreen
import me.lokmvne.common.navigation.SignInScreen

class Screens(navHostController: NavHostController) {


    val toSignIn: () -> Unit = {
        navHostController.navigate(SignInScreen) {
            popUpTo(SignInScreen) {
                inclusive = true
            }
        }
    }

    val toOnBoarding: () -> Unit = {
        navHostController.navigate(OnBoardingScreen) {
            popUpTo(OnBoardingScreen) {
                inclusive = true
            }
        }
    }

    val goToProfile: () -> Unit = {
        navHostController.navigate(NavToDoProfileScreen)
    }

    val todoList: () -> Unit = {
        navHostController.navigate(NavToDoListScreen) {
            popUpTo(NavToDoListScreen) {
                inclusive = true
            }
        }
    }

    val todoTask: (Long) -> Unit = { taskId ->
        navHostController.navigate(NavToDoTaskScreen(taskId))
    }
}