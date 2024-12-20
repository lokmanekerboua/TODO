package me.lokmvne.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun ToDoTextField(
    txt: String,
    placeholder: String = "",
    label: String = "",
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    roundedCorner: Int = 10,
    trailingIcon: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        value = txt,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth(),
        enabled = true,
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        trailingIcon = { trailingIcon() },
        isError = false,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        maxLines = maxLines,
        minLines = 1,
        interactionSource = null,
        shape = RoundedCornerShape(roundedCorner.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
            errorTextColor = MaterialTheme.colorScheme.error,

            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.error,

            cursorColor = MaterialTheme.colorScheme.secondary,
            errorCursorColor = MaterialTheme.colorScheme.error,

            selectionColors = null,

            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,

            focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            errorLeadingIconColor = MaterialTheme.colorScheme.onBackground,

            focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onBackground,
            errorTrailingIconColor = MaterialTheme.colorScheme.onBackground,

            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            disabledLabelColor = MaterialTheme.colorScheme.onBackground,
            errorLabelColor = MaterialTheme.colorScheme.error,

            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            errorPlaceholderColor = MaterialTheme.colorScheme.error,

            focusedSupportingTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onBackground,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onBackground,
            errorSupportingTextColor = MaterialTheme.colorScheme.error,

            focusedPrefixColor = MaterialTheme.colorScheme.primary,
            unfocusedPrefixColor = MaterialTheme.colorScheme.primary,
            disabledPrefixColor = MaterialTheme.colorScheme.primary,
            errorPrefixColor = MaterialTheme.colorScheme.primary,

            focusedSuffixColor = MaterialTheme.colorScheme.primary,
            unfocusedSuffixColor = MaterialTheme.colorScheme.primary,
            disabledSuffixColor = MaterialTheme.colorScheme.primary,
            errorSuffixColor = MaterialTheme.colorScheme.primary

        )
    )
}