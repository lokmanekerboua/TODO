package me.lokmvne.common.navigation

import kotlinx.serialization.Serializable

@Serializable
object SignInScreen

@Serializable
object OnBoardingScreen

@Serializable
object SplashScreen

@Serializable
object ToDoNavGraph

@Serializable
object NavToDoListScreen

@Serializable
object NavToDoProfileScreen

@Serializable
data class NavToDoTaskScreen(
    val taskId: Long = -1L,
)