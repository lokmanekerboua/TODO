package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AddButton(navigateToTaskScreen: (Long) -> Unit) {
    FloatingActionButton(
        onClick = { navigateToTaskScreen(-1) },
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        shape = RoundedCornerShape(50.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Task"
        )
    }
}