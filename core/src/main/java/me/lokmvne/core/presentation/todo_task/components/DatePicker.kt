package me.lokmvne.core.presentation.todo_task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoDatePickerDialog(onDismiss: () -> Unit, onConfirm: (pickedDate: Long?) -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    )
    DatePickerDialog(
        modifier = Modifier.padding(horizontal = 10.dp),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}