package me.lokmvne.todo.presentstion.navigation.destinations

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.lokmvne.common.navigation.SignInScreen
import me.lokmvne.todo.presentstion.signIn.SignInScreen

fun NavGraphBuilder.signInScreenComposable(
    navigate: () -> Unit,
    context: Context
) {
    composable<SignInScreen> {
        SignInScreen(navigate, context)
    }
}