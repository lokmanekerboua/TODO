package me.lokmvne.todo.presentstion.signIn

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.lokmvne.auth.repository.GoogleAuthRepoImpl
import me.lokmvne.common.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthRepoImpl: GoogleAuthRepoImpl
) : ViewModel() {
    private val _signInEvents = Channel<SignInEvents>()
    val events = _signInEvents.receiveAsFlow()

    val snackBarState = SnackbarHostState()

    fun googleSignIn(context: Context) {
        viewModelScope.launch {
            googleAuthRepoImpl.signIn(context).let {
                when (it) {
                    is Resource.Error -> {
                        _signInEvents.send(
                            SignInEvents.ShowSignInError(
                                it.message ?: "An error occurred"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _signInEvents.send(SignInEvents.GoToHome)
                    }

                    else -> {
                        _signInEvents.send(
                            SignInEvents.ShowSignInError(
                                it.message ?: "An error occurred"
                            )
                        )
                    }
                }
            }
        }
    }
}

sealed class SignInEvents {
    data object GoToHome : SignInEvents()
    data class ShowSignInError(val message: String) : SignInEvents()
}