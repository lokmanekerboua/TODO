package me.lokmvne.todo.presentstion.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.lokmvne.common.utils.ObserveAsEvents
import me.lokmvne.core.presentation.navigation.Screens
import me.lokmvne.todo.R

@Composable
fun SplashScreen(screens: Screens) {
    val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>()

    splashScreenViewModel.readFirstTime()
    ObserveAsEvents(
        flow = splashScreenViewModel.events,
        onEvent = {
            when (it) {
                NavToScreen.OnBoarding -> {
                    screens.toOnBoarding()
                }

                NavToScreen.SignIn -> {
                    screens.toSignIn()
                }

                NavToScreen.Home -> {
                    screens.todoList()
                }
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = null,
            modifier = Modifier.size(288.dp)
        )
    }
}