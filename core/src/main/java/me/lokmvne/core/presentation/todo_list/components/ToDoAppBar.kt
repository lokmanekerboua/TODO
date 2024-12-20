package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.unit.dp
import me.lokmvne.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoToAppBar(
    isTopAppBarDropdownExpanded: Boolean,
    showOrderingSection: () -> Unit,
    showDropDown: () -> Unit,
    hideDropDown: () -> Unit,
    showDeleteDialog: () -> Unit,
    showNavigationDrawer: () -> Unit,
) {

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text("TODO")
            }
        },
        windowInsets = WindowInsets.statusBars,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "App Menu",
                modifier = Modifier.clickable {
                    showNavigationDrawer()
                }
            )
        },
        actions = {

            Icon(
                painter = painterResource(R.drawable.tablersortdescending),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        showOrderingSection()
                    }
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
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