package me.lokmvne.core.presentation.todo_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import me.lokmvne.common.utils.ObserveAsEvents
import me.lokmvne.compose.ui.theme.colorCard2
import me.lokmvne.compose.ui.theme.colorCard4
import me.lokmvne.core.presentation.todo_list.components.AddButton
import me.lokmvne.core.presentation.todo_list.components.DeleteDialog
import me.lokmvne.core.presentation.todo_list.components.OrderingSection
import me.lokmvne.core.presentation.todo_list.components.SwipeToDoItem
import me.lokmvne.core.presentation.todo_list.components.ToDoSearchBox
import me.lokmvne.core.presentation.todo_list.components.ToDoToAppBar
import me.lokmvne.core.presentation.todo_list.components.TodoItem
import me.lokmvne.core.utils.SnackBarController

@Composable
fun TodoListScreen(
    navigateToTaskScreen: (Int) -> Unit,
) {
    val viewModel = hiltViewModel<TodoListViewModel>()
    val scope = rememberCoroutineScope()

    // Observing the events for the snackbar. see (core/utils/SnackBarController.kt)
    // and (core/utils/ObserveAsEvents.kt) for more information
    ObserveAsEvents(
        flow = SnackBarController.events,
        viewModel.tasksState.snackBarState
    ) { event ->
        scope.launch {
            viewModel.tasksState.snackBarState.currentSnackbarData?.dismiss()
            val result = viewModel.tasksState.snackBarState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(viewModel.tasksState.snackBarState) },
            topBar = {
                ToDoToAppBar(
                    isTopAppBarDropdownExpanded = viewModel.tasksState.isTopAppBarDropdownExpanded,
                    showOrderingSection = { viewModel.onEvent(TasksListEvents.ShowOrderingSection) },
                    showDropDown = { viewModel.onEvent(TasksListEvents.ExpandTopAppBarDropdown) },
                    hideDropDown = { viewModel.onEvent(TasksListEvents.HideTopAppBarDropdown) },
                    showDeleteDialog = { viewModel.onEvent(TasksListEvents.ShowDeleteDialog) },
                    showSearchBox = { viewModel.onEvent(TasksListEvents.ShowSearchBox) }
                )
            },
            floatingActionButton = { AddButton(navigateToTaskScreen) },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//-----------------------------Search Box----------------------------------------------
                AnimatedVisibility(
                    visible = viewModel.tasksState.isSearchBoxVisible
                ) {
                    ToDoSearchBox(
                        searchQuery = viewModel.searchQuery,
                        onSearchValueChange = {
                            viewModel.searchQuery = it
                            viewModel.onEvent(TasksListEvents.SearchTask(viewModel.searchQuery))
                        },
                        onSearch = {
                            viewModel.onEvent(TasksListEvents.SearchTask(viewModel.searchQuery))
                        }
                    )
                }
//-----------------------------Ordering Section----------------------------------------------
                AnimatedVisibility(
                    visible = viewModel.tasksState.isOrderingSectionVisible,
                ) {
                    OrderingSection(
                        modifier = Modifier.padding(10.dp),
                        taskOrder = viewModel.tasksState.tasksOrder,
                        onTaskOrderChange = {
                            viewModel.onEvent(TasksListEvents.Order(it))
                        }
                    )
                }

//-----------------------------Tasks List----------------------------------------------
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                    ) {
                        Text(text = "TOP", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    LazyRow {
                        items(viewModel.tasksState.highPriorityTasks) {
                            key("${it.id}${it.version}") {
                                TodoItem(
                                    todoTask = it,
                                    illustration = it.illustration,
                                    modifier = Modifier
                                        .size(200.dp, 130.dp)
                                        .padding(10.dp),
                                    containerColor = Color(it.taskColor),
                                    contentColor = colorCard4,
                                    descriptionMaxLines = 3,
                                    onClick = { navigateToTaskScreen(it.id) }
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                ) {
                    Text(text = "All Tasks", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                LazyColumn {
                    items(viewModel.tasksState.todoTasks) {
                        key("${it.id}${it.version}") {
                            SwipeToDoItem(
                                todoTask = it,
                                onClick = {
                                    navigateToTaskScreen(it.id)
                                },
                                onDelete = {
                                    viewModel.onEvent(TasksListEvents.DeleteTask(it))
                                },
                                illustration = it.illustration,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                containerColor = Color(it.taskColor),
                                contentColor = colorCard4,
                                descriptionMaxLines = 1,
                            )
                        }
                    }
                }
            }
        }

        if (viewModel.tasksState.isDeleteDialogVisible) {
            DeleteDialog(
                onConfirm = { viewModel.onEvent(TasksListEvents.DeleteAllTasks) },
                onDismiss = { viewModel.onEvent(TasksListEvents.HideDeleteDialog) }
            )
        }
    }
}