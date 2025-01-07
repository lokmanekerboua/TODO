package me.lokmvne.core.presentation.todo_task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.lokmvne.core.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTimePickerDialog(onDismiss: () -> Unit, onConfirm: (pickedDate: TimePickerState) -> Unit) {
    val currentTime = Calendar.getInstance()
    val timeState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    AlertDialog(
        modifier = Modifier.padding(horizontal = 10.dp),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    onConfirm(timeState)
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }

        },
        dismissButton = {
            TextButton(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.dismiss))
            }
        },
        text = {
            TimePicker(
                timeState
            )
        }
    )
}