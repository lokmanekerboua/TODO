package me.lokmvne.core.presentation.todo_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import me.lokmvne.core.presentation.todo_list.components.AddButton
import me.lokmvne.core.presentation.todo_list.components.DeleteDialog
import me.lokmvne.core.presentation.todo_list.components.OrderingSection
import me.lokmvne.core.presentation.todo_list.components.SwipeToDoItem
import me.lokmvne.core.presentation.todo_list.components.ToDoSearchBox
import me.lokmvne.core.presentation.todo_list.components.ToDoToAppBar
import me.lokmvne.core.utils.ObserveAsEvents
import me.lokmvne.core.utils.SnackBarController

@Composable
fun TodoListScreen(navigateToTaskScreen: (Int) -> Unit) {
    val viewModel = hiltViewModel<TodoListViewModel>()

    val scope = rememberCoroutineScope()

    // Observing the events for the snackbar. see (core/utils/SnackBarController.kt)
    // and (core/utils/ObserveAsEvents.kt) for more information
    ObserveAsEvents(
        flow = SnackBarController.events,
        viewModel.snackBarHostState
    ) { event ->
        scope.launch {
            viewModel.snackBarHostState.currentSnackbarData?.dismiss()
            val result = viewModel.snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(viewModel.snackBarHostState) },
        topBar = {
            ToDoToAppBar(
                isTopAppBarDropdownExpanded = viewModel.tasksState.isTopAppBarDropdownExpanded,
                showOrderingSection = { viewModel.onEvent(TasksListEvents.ShowOrderingSection) },
                showDropDown = { viewModel.onEvent(TasksListEvents.ExpandTopAppBarDropdown) },
                hideDropDown = { viewModel.onEvent(TasksListEvents.HideTopAppBarDropdown) },
                showDeleteDialog = { viewModel.onEvent(TasksListEvents.ShowDeleteDialog) },
                showNavigationDrawer = {}
            )
        },
        floatingActionButton = { AddButton(navigateToTaskScreen) },
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//-----------------------------Search Box----------------------------------------------
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
                                }
                            )
                        }
                    }
                }
            }
//----------------------------------Delete Dialog-----------------------------------------
            if (viewModel.tasksState.isDeleteDialogVisible) {
                DeleteDialog(
                    onConfirm = { viewModel.onEvent(TasksListEvents.DeleteAllTasks) },
                    onDismiss = { viewModel.onEvent(TasksListEvents.HideDeleteDialog) }
                )
            }
        }
    }
}