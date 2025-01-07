package me.lokmvne.core.presentation.todo_task

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import androidx.hilt.navigation.compose.hiltViewModel
import me.lokmvne.compose.components.ToDoTextField
import me.lokmvne.compose.ui.theme.colorCard4
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


@Composable
fun ToDoTaskScreen(
    navigateToListScreen: () -> Unit,
    taskId: Long,
) {
    val viewModel = hiltViewModel<ToDoTaskViewModel>()
    ToDoTaskSkaleton(
        viewModel = viewModel,
        taskId = taskId,
        navigateToListScreen = navigateToListScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTaskSkaleton(
    viewModel: ToDoTaskViewModel,
    taskId: Long,
    navigateToListScreen: () -> Unit
) {
    val context = LocalContext.current

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OptionsCard(
                    onClick = {
                        viewModel.singleTaskState = viewModel.singleTaskState.copy(
                            isDatePickerShowed = true
                        )
                    },
                    title = stringResource(R.string.order_date),
                    content = "${
                        viewModel.singleTaskState.date.let {
                            dateTimeToString(
                                it,
                                "yyyy-MM-dd"
                            )
                        }
                    }",
                    Color(viewModel.singleTaskState.taskColor),
                    colorCard4,
                )

                OptionsCard(
                    onClick = {
                        viewModel.singleTaskState = viewModel.singleTaskState.copy(
                            isTimePickerShowed = true
                        )
                    },
                    title = stringResource(R.string.time),
                    content = "${
                        viewModel.singleTaskState.time.let {
                            dateTimeToString(
                                it,
                                "HH:mm"
                            )
                        }
                    }",
                    Color(viewModel.singleTaskState.taskColor),
                    colorCard4,
                )

                ExposedDropdownMenuBox(
                    expanded = viewModel.singleTaskState.isIllustExpended,
                    onExpandedChange = {
                        viewModel.singleTaskState = viewModel.singleTaskState.copy(
                            isIllustExpended = it
                        )
                    }
                ) {
                    Card(
                        modifier = Modifier
                            .size(100.dp, 60.dp)
                            .clickable { }
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        elevation = CardDefaults.cardElevation(5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(viewModel.singleTaskState.taskColor),
                            contentColor = colorCard4,
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(viewModel.singleTaskState.illustration),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = viewModel.singleTaskState.isIllustExpended,
                        onDismissRequest = {
                            viewModel.singleTaskState = viewModel.singleTaskState.copy(
                                isIllustExpended = false
                            )
                        },
                        modifier = Modifier.width(150.dp),
                        matchTextFieldWidth = false,
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
            }

            ToDoTextField(
                txt = viewModel.singleTaskState.title,
                label = stringResource(R.string.order_title),
                onValueChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        title = it
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
                    label = stringResource(R.string.order_priority),
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
                expanded = viewModel.singleTaskState.isColorExpended,
                onExpandedChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        isColorExpended = it
                    )
                }
            ) {
                ToDoTextField(
                    txt = "",
                    label = "",
                    readOnly = true,
                    containerColor = Color(viewModel.singleTaskState.taskColor),
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
                label = stringResource(R.string.description),
                modifier = Modifier.height(300.dp),
                maxLines = 10,
                onValueChange = {
                    viewModel.singleTaskState = viewModel.singleTaskState.copy(
                        description = it
                    )
                }
            )

            if (taskId == -1L) {
                TaskButton(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            if (viewModel.alarmManager.canScheduleExactAlarms()) {
                                viewModel.onEvent(TaskEvents.AddTask)
                                navigateToListScreen()
                            } else {
                                val intent = Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                                    flags = FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(intent)
                            }
                        } else {
                            viewModel.onEvent(TaskEvents.AddTask)
                            navigateToListScreen()
                        }
                    },
                    title = stringResource(R.string.save),
                    containerColor = Color(viewModel.singleTaskState.taskColor),
                    contentColor = colorCard4
                )
            } else {
                LaunchedEffect(true) {
                    viewModel.onEvent(TaskEvents.GetSelectedTask(taskId))
                }
                TaskButton(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            if (viewModel.alarmManager.canScheduleExactAlarms()) {
                                viewModel.onEvent(TaskEvents.UpdateTask(taskId))
                                navigateToListScreen()
                            } else {
                                val intent = Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                                    flags = FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(intent)
                            }
                        } else {
                            viewModel.onEvent(TaskEvents.UpdateTask(taskId))
                            navigateToListScreen()
                        }
                    },
                    title = stringResource(R.string.update),
                    containerColor = Color(viewModel.singleTaskState.taskColor),
                    contentColor = colorCard4
                )
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

@Composable
fun OptionsCard(
    onClick: () -> Unit,
    title: String,
    content: String,
    containerColor: Color,
    contentColor: Color
) {
    Card(
        modifier = Modifier
            .size(100.dp, 60.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(contentColor),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = content,
                    color = containerColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 1.3.em,
                    modifier = Modifier
                        .height(15.dp)
                        .padding(horizontal = 5.dp)
                )
            }
        }
    }
}

@Composable
fun TaskButton(
    onClick: () -> Unit,
    title: String,
    containerColor: Color,
    contentColor: Color
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(16.dp)
            .height(45.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            containerColor = containerColor
        )
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
    }
}

val colorsList = listOf(
    "YELLOW" to 0xFFf5f378,
    "MAGENTA" to 0xFFdcc2ff,
    "RED" to 0xFFed724c,
    "GREEN" to 0xFFadebbe
)

val illustrationsList = listOf(
    "sticker1" to R.drawable.illust1,
    "sticker2" to R.drawable.illust2,
    "sticker3" to R.drawable.illust3,
    "sticker4" to R.drawable.illust4,
    "sticker5" to R.drawable.illust5,
    "sticker6" to R.drawable.illust6,
    "sticker7" to R.drawable.illust7,
    "sticker8" to R.drawable.illust8,
    "sticker9" to R.drawable.illust9,
    "sticker10" to R.drawable.illust10,
    "sticker11" to R.drawable.illust11,
)