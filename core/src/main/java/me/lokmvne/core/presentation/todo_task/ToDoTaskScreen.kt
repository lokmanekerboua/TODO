package me.lokmvne.core.presentation.todo_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
import me.lokmvne.core.R
import me.lokmvne.core.data.utils.Priority
import me.lokmvne.core.presentation.todo_task.components.DropDownColorMenuItem
import me.lokmvne.core.presentation.todo_task.components.DropDownIllustrationMenuItem
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
    taskId: Long,
) {
    val viewModel = hiltViewModel<ToDoTaskViewModel>()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToDoTextField(
                txt = viewModel.singleTaskState.title,
                label = "Title",
                onValueChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        title = it
                    )
                }
            )

            ToDoTextField(
                txt = viewModel.singleTaskState.date.let { dateTimeToString(it, "yyyy-MM-dd") }
                    ?: "",
                label = "Date",
                readOnly = true,
                placeholder = "yyyy-MM-dd",
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.singleTaskState = viewModel.singleTaskState.copy(
                                isDatePickerShowed = true
                            )
                        }
                    )
                }
            )
            ToDoTextField(
                txt = viewModel.singleTaskState.time.let { dateTimeToString(it, "HH:mm") } ?: "",
                label = "Time",
                readOnly = true,
                placeholder = "HH:mm",
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.singleTaskState = viewModel.singleTaskState.copy(
                                isTimePickerShowed = true
                            )
                        }
                    )
                }
            )

            ExposedDropdownMenuBox(
                expanded = viewModel.singleTaskState.isPriorityExpended,
                onExpandedChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        isPriorityExpended = it
                    )
                }
            ) {
                ToDoTextField(
                    txt = viewModel.singleTaskState.priority.name,
                    label = "Priority",
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(viewModel.singleTaskState.isPriorityExpended) },
                    onValueChange = {}
                )

                ExposedDropdownMenu(
                    expanded = viewModel.singleTaskState.isPriorityExpended,
                    onDismissRequest = {
                        viewModel.singleTaskState = viewModel.singleTaskState.copy(
                            isPriorityExpended = false
                        )
                    },
                    matchTextFieldWidth = true,
                    containerColor = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp),
                    tonalElevation = 10.dp,
                    shadowElevation = 10.dp
                ) {
                    Priority.entries.forEach { priorityItem ->
                        DropDownMenuItem(priority = priorityItem, onClick = {
                            viewModel.singleTaskState = viewModel.singleTaskState.copy(
                                priority = it,
                                isPriorityExpended = false
                            )
                        })
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = viewModel.singleTaskState.isIllustExpended,
                onExpandedChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        isIllustExpended = it
                    )
                }
            ) {
                ToDoTextField(
                    txt = viewModel.singleTaskState.illustText,
                    label = "Illustration",
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(viewModel.singleTaskState.isIllustExpended) },
                    onValueChange = {}
                )

                ExposedDropdownMenu(
                    expanded = viewModel.singleTaskState.isIllustExpended,
                    onDismissRequest = {
                        viewModel.singleTaskState = viewModel.singleTaskState.copy(
                            isIllustExpended = false
                        )
                    },
                    matchTextFieldWidth = true,
                    containerColor = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp),
                    tonalElevation = 10.dp,
                    shadowElevation = 10.dp
                ) {
                    illustrationsList.forEach { illust ->
                        DropDownIllustrationMenuItem(
                            title = illust.first,
                            illustration = illust.second,
                            onClick = { illustrationTitle, illustration ->
                                viewModel.singleTaskState = viewModel.singleTaskState.copy(
                                    isIllustExpended = false,
                                    illustText = illustrationTitle,
                                    illustration = illustration
                                )
                            }
                        )
                    }
                }
            }


            ExposedDropdownMenuBox(
                expanded = viewModel.singleTaskState.isColorExpended,
                onExpandedChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        isColorExpended = it
                    )
                }
            ) {
                ToDoTextField(
                    txt = viewModel.singleTaskState.colorText,
                    label = "Task Color",
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(viewModel.singleTaskState.isColorExpended) },
                    onValueChange = {}
                )

                ExposedDropdownMenu(
                    expanded = viewModel.singleTaskState.isColorExpended,
                    onDismissRequest = {
                        viewModel.singleTaskState = viewModel.singleTaskState.copy(
                            isColorExpended = false
                        )
                    },
                    matchTextFieldWidth = true,
                    containerColor = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp),
                    tonalElevation = 10.dp,
                    shadowElevation = 10.dp
                ) {
                    colorsList.forEach { color ->
                        DropDownColorMenuItem(
                            title = color.first,
                            color = color.second,
                            onClick = { title, value ->
                                viewModel.singleTaskState = viewModel.singleTaskState.copy(
                                    isColorExpended = false,
                                    colorText = title,
                                    taskColor = value
                                )
                            }
                        )
                    }
                }
            }

            ToDoTextField(
                txt = viewModel.singleTaskState.description,
                label = "Description",
                onValueChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        description = it
                    )
                }
            )

            if (taskId == -1L) {
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
                    viewModel.onEvent(TaskEvents.GetSelectedTask(taskId))
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

        if (viewModel.singleTaskState.isDatePickerShowed) {
            ToDoDatePickerDialog(
                onDismiss = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        isDatePickerShowed = false
                    )
                },
                onConfirm = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        date = Instant.ofEpochMilli(it ?: 0).atZone(ZoneOffset.UTC).toLocalDate()
                    )
                }
            )
        }

        if (viewModel.singleTaskState.isTimePickerShowed) {
            ToDoTimePickerDialog(
                onDismiss = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        isTimePickerShowed = false
                    )
                },
                onConfirm = {
                    val time = it.hour * 3600 + it.minute * 60L
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        time = LocalTime.ofSecondOfDay(time)
                    )
                }
            )
        }

    }
}

val colorsList = listOf(
    "YELLOW" to 0xFFf5f378,
    "MAGENTA" to 0xFFdcc2ff,
    "RED" to 0xFFed724c
)

val illustrationsList = listOf(
    "illustration1" to R.drawable.illust1,
    "illustration2" to R.drawable.illust2,
    "illustration3" to R.drawable.illust3,
    "illustration4" to R.drawable.illust4,
    "illustration5" to R.drawable.illust5,
    "illustration6" to R.drawable.illust6,
    "illustration7" to R.drawable.illust7,
    "illustration8" to R.drawable.illust8,
    "illustration9" to R.drawable.illust9,
    "illustration10" to R.drawable.illust10,
    "illustration11" to R.drawable.illust11,
)
