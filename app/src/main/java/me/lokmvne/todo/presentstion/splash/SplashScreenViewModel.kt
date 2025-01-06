package me.lokmvne.todo.presentstion.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.lokmvne.auth.repository.GoogleAuthRepoImpl
import me.lokmvne.common.data.data_store.ToDoPreferences
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val todoPreferences: ToDoPreferences,
    val googleAuthRepoImpl: GoogleAuthRepoImpl
) : ViewModel() {
    private val _events = Channel<NavToScreen>()
    val events = _events.receiveAsFlow()

    fun readFirstTime() {
        viewModelScope.launch {
            todoPreferences.getFirstTime().collect {
                delay(1000)
                if (it) {
                    _events.send(NavToScreen.OnBoarding)
                } else {
                    googleAuthRepoImpl.checkIfGoogleFirebaseUserIsSignedIn().let {
                        if (it) {
                            _events.send(NavToScreen.Home)
                        } else {
                            _events.send(NavToScreen.SignIn)
                        }
                    }
                }
            }
        }
    }
}

sealed class NavToScreen {
    data object OnBoarding : NavToScreen()
    data object SignIn : NavToScreen()
    data object Home : NavToScreen()
}