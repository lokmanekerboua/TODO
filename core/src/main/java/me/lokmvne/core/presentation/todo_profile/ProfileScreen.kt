package me.lokmvne.core.presentation.todo_profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.lokmvne.core.R
import me.lokmvne.core.presentation.todo_profile.composable.SignOutDialog

@Composable
fun ProfileScreen(
    navigateToListScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    val viewModel = hiltViewModel<ProfileViewModel>()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = viewModel.profileState.toDoUser.photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewModel.profileState.toDoUser.name,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewModel.profileState.toDoUser.email,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.signOut),
                    color = Color.Red,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.clickable {
                        viewModel.showSignOutDialog()
                    }
                )
            }
        }

        if (viewModel.showSignOutDialog) {
            SignOutDialog(
                onConfirm = {
                    viewModel.signOut()
                    navigateToSignInScreen()
                },
                onDismiss = { viewModel.hideSignOutDialog() }
            )
        }
    }
}