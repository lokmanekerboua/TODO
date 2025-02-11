package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.lokmvne.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoToAppBar(
    photoUrl: String,
    goToProfile: () -> Unit,
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
                Text(
                    text = "TODO",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(48.dp)
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .clickable { goToProfile() }
                )
            }
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
                    text = { Text(stringResource(R.string.deleteAll)) },
                    onClick = {
                        showDeleteDialog()
                        hideDropDown()
                    }
                )
            }
        }
    )
}