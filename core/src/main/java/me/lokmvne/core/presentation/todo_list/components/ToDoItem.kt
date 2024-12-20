package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.lokmvne.core.domain.model.ToDoTask
import java.time.format.DateTimeFormatter


@Composable
fun SwipeToDoItem(
    todoTask: ToDoTask,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDelete: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    val swipeState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            scope.launch {
                delay(1000)
                if (it == SwipeToDismissBoxValue.EndToStart) {
                    onDelete()
                }
            }
            true
        },
        positionalThreshold = { it * 0.80f }
    )
    AnimatedVisibility(
        visible = swipeState.currentValue != SwipeToDismissBoxValue.EndToStart,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 500),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = swipeState,
            modifier = Modifier.height(90.dp),
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 10.dp),
                        tint = Color.White
                    )
                }
            }
        ) {
            TodoItem(
                todoTask = todoTask,
                modifier = modifier,
                onClick = onClick
            )
        }
    }
}


@Composable
fun TodoItem(
    todoTask: ToDoTask,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .height(90.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onClick()
            }
            .padding(vertical = 5.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = todoTask.title,
                modifier = Modifier.weight(9f),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Canvas(
                modifier = Modifier
                    .size(15.dp)
                    .weight(1f)
            ) {
                drawCircle(
                    color = todoTask.priority.color
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = todoTask.description,
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${todoTask.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                fontSize = 12.sp
            )
            Text(
                text = "${todoTask.time?.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                fontSize = 12.sp
            )
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
    }
}