package me.lokmvne.core.presentstion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.lokmvne.compose.components.ToDoTextField
import me.lokmvne.core.data.models.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTaskScreen(navHostController: NavHostController, taskId: Int) {
    val viewModel = hiltViewModel<ToDoTaskViewModel>()
    val isExpended = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToDoTextField(
            txt = viewModel.title.value,
            label = "Title",
            onValueChange = {
                viewModel.title.value = it
            }
        )
        ToDoTextField(
            txt = viewModel.description.value,
            label = "Description",
            onValueChange = {
                viewModel.description.value = it
            }
        )

        ExposedDropdownMenuBox(
            expanded = isExpended.value,
            onExpandedChange = { isExpended.value = it }
        ) {
            ToDoTextField(
                txt = viewModel.priority.value.name,
                label = "Priority",
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpended.value) },
                onValueChange = {}
            )

            ExposedDropdownMenu(
                expanded = isExpended.value,
                onDismissRequest = { isExpended.value = false },
                matchTextFieldWidth = true,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp),
                tonalElevation = 10.dp,
                shadowElevation = 10.dp
            ) {
                Priority.entries.forEach {
                    DropDownMenuItem(priority = it, onClick = {
                        viewModel.priority.value = it
                        isExpended.value = false
                    })
                }
            }
        }

        if (taskId == -1) {
            Button(
                onClick = {
                    viewModel.addTask()
                    navHostController.popBackStack()
                }
            ) {
                Text(text = "Save")
            }
        } else {
            Button(
                onClick = {
                    viewModel.updateTask()
                    navHostController.popBackStack()
                }
            ) {
                Text(text = "Update")
            }
        }
    }
}

@Composable
fun DropDownMenuItem(priority: Priority, onClick: (priority: Priority) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(priority)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(priority.name)
    }
}