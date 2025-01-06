package me.lokmvne.todo.presentstion.signIn

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import me.lokmvne.common.utils.ObserveAsEvents
import me.lokmvne.todo.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    navigate: () -> Unit,
    context: Context
) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = viewModel.events,
        onEvent = {
            when (it) {
                SignInEvents.GoToHome -> {
                    navigate()
                }

                is SignInEvents.ShowSignInError -> {
                    scope.launch {
                        viewModel.snackBarState.showSnackbar(
                            message = it.message,
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(viewModel.snackBarState) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginButton(
                        text = "Continue with Google",
                        icon = R.drawable.devicongoogle,
                        description = "Google",
                        onclicked = {
                            viewModel.googleSignIn(context)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LoginButton(
                        text = "Continue with Facebook",
                        icon = R.drawable.logosfacebook,
                        description = "Facebook",
                        onclicked = {}
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LoginButton(
                        text = "Continue with Apple",
                        icon = R.drawable.cibapple,
                        description = "Apple",
                        onclicked = {}
                    )
                }
            }
        }
    }
}

@Composable
fun LoginButton(
    text: String = "",
    icon: Int = R.drawable.logo1,
    description: String = "",
    onclicked: () -> Unit = {}
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 0.dp)
            .height(45.dp),
        onClick = { onclicked() },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            //modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = description,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}