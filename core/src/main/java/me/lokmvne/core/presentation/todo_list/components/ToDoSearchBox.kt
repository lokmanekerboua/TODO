package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.lokmvne.compose.components.ToDoTextField
import me.lokmvne.core.R

@Composable
fun ToDoSearchBox(
    searchQuery: String,
    onSearchValueChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    ToDoTextField(
        txt = searchQuery,
        placeholder = stringResource(R.string.search),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onValueChange = {
            onSearchValueChange(it)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onSearch(searchQuery)
                }
            )
        },
        maxLines = 1,
        roundedCorner = 30
    )
}