package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import me.lokmvne.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoToAppBar(
    isTopAppBarDropdownExpanded: Boolean,
    showOrderingSection: () -> Unit,
    showDropDown: () -> Unit,
    hideDropDown: () -> Unit,
    showDeleteDialog: () -> Unit,
    showSearchBox: () -> Unit,
) {

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text("TODO")
            }
        },
        windowInsets = WindowInsets.statusBars,
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.menu),
                contentDescription = "App Menu",
                modifier = Modifier.clickable {}
            )
        },
        actions = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.clickable {
                    showSearchBox()
                }
            )

            Icon(
                painter = painterResource(R.drawable.sort),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        showOrderingSection()
                    }
            )
            Icon(
                painter = painterResource(R.drawable.dots),
                contentDescription = null,
                modifier = Modifier.clickable {
                    showDropDown()
                }
            )

            DropdownMenu(
                expanded = isTopAppBarDropdownExpanded,
                onDismissRequest = { hideDropDown() }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete All") },
                    onClick = {
                        showDeleteDialog()
                        hideDropDown()
                    }
                )
            }
        }
    )
}