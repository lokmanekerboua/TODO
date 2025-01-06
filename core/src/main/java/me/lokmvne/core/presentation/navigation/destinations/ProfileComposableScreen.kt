package me.lokmvne.core.presentation.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.lokmvne.common.navigation.NavToDoProfileScreen
import me.lokmvne.core.presentation.todo_profile.ProfileScreen

fun NavGraphBuilder.profileComposableScreen(
    navigateToListScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    composable<NavToDoProfileScreen> {
        ProfileScreen(navigateToListScreen, navigateToSignInScreen)
    }
}