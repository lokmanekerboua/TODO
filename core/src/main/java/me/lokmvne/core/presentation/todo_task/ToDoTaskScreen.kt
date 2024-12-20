package me.lokmvne.core.presentation.todo_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.lokmvne.compose.components.ToDoTextField
import me.lokmvne.core.data.utils.Priority
import me.lokmvne.core.presentation.todo_task.components.DropDownMenuItem
import me.lokmvne.core.presentation.todo_task.components.ToDoDatePickerDialog
import me.lokmvne.core.presentation.todo_task.components.ToDoTimePickerDialog
import me.lokmvne.core.utils.dateTimeToString
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTaskScreen(
    navigateToListScreen: () -> Unit,
    taskId: Int,
) {
    val viewModel = hiltViewModel<ToDoTaskViewModel>()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
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
                txt = viewModel.date.value?.let { dateTimeToString(it, "yyyy-MM-dd") } ?: "",
                label = "Date",
                readOnly = true,
                placeholder = "yyyy-MM-dd",
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.isDatePickerShowed = true
                        }
                    )
                }
            )
            ToDoTextField(
                txt = viewModel.time.value?.let { dateTimeToString(it, "HH:mm") } ?: "",
                label = "Time",
                readOnly = true,
                placeholder = "HH:mm",
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.isTimePickerShowed = true
                        }
                    )
                }
            )

            ExposedDropdownMenuBox(
                expanded = viewModel.isPriorityExpended,
                onExpandedChange = { viewModel.isPriorityExpended = it }
            ) {
                ToDoTextField(
                    txt = viewModel.priority.value.name,
                    label = "Priority",
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(viewModel.isPriorityExpended) },
                    onValueChange = {}
                )

                ExposedDropdownMenu(
                    expanded = viewModel.isPriorityExpended,
                    onDismissRequest = { viewModel.isPriorityExpended = false },
                    matchTextFieldWidth = true,
                    containerColor = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp),
                    tonalElevation = 10.dp,
                    shadowElevation = 10.dp
                ) {
                    Priority.entries.forEach { priorityItem ->
                        DropDownMenuItem(priority = priorityItem, onClick = {
                            viewModel.priority.value = it
                            viewModel.isPriorityExpended = false
                        })
                    }
                }
            }

            ToDoTextField(
                txt = viewModel.description.value,
                label = "Description",
                onValueChange = {
                    viewModel.description.value = it
                }
            )

            if (taskId == -1) {
                Button(
                    onClick = {
                        viewModel.onEvent(TaskEvents.AddTask)
                        navigateToListScreen()
                    }
                ) {
                    Text(text = "Save")
                }
            } else {
                LaunchedEffect(true) {
                    viewModel.getSelectedTask(taskId)
                }
                Button(
                    onClick = {
                        viewModel.onEvent(TaskEvents.UpdateTask(taskId))
                        navigateToListScreen()
                    }
                ) {
                    Text(text = "Update")
                }
            }
        }

        if (viewModel.isDatePickerShowed) {
            ToDoDatePickerDialog(
                onDismiss = { viewModel.isDatePickerShowed = false },
                onConfirm = {
                    viewModel.date.value =
                        Instant.ofEpochMilli(it ?: 0).atZone(ZoneOffset.UTC).toLocalDate()
                }
            )
        }

        if (viewModel.isTimePickerShowed) {
            ToDoTimePickerDialog(
                onDismiss = {
                    viewModel.isTimePickerShowed = false
                },
                onConfirm = {
                    val time = it.hour * 3600 + it.minute * 60L
                    viewModel.time.value = LocalTime.ofSecondOfDay(time)
                }
            )
        }

    }
}


