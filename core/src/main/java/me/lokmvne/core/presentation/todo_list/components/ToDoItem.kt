package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.lokmvne.core.domain.model.ToDoTask
import java.time.format.DateTimeFormatter


@Composable
fun SwipeToDoItem(
    todoTask: ToDoTask,
    illustration: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    descriptionMaxLines: Int = 1,
    onDelete: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    val swipeState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            scope.launch {
                delay(900)
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
        ) + fadeOut(),
        enter = expandHorizontally(
            animationSpec = tween(durationMillis = 400),
            expandFrom = Alignment.End
        ) + fadeIn()
    ) {
        SwipeToDismissBox(
            state = swipeState,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .height(85.dp),
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                val color = if (swipeState.currentValue != swipeState.dismissDirection) Color.Red
                else MaterialTheme.colorScheme.background
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color),
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
                illustration = illustration,
                modifier = modifier,
                containerColor = containerColor,
                contentColor = contentColor,
                descriptionMaxLines = descriptionMaxLines,
                onClick = onClick
            )
        }
    }
}


@Composable
fun TodoItem(
    todoTask: ToDoTask,
    illustration: Int,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    descriptionMaxLines: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(85.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(illustration),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(70.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todoTask.title,
                        modifier = Modifier.weight(9f),
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todoTask.description,
                        maxLines = descriptionMaxLines,
                        fontSize = 12.sp,
                        lineHeight = 1.5.em,
                        color = contentColor,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(contentColor),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "${todoTask.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                            fontSize = 10.sp,
                            color = containerColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 1.5.em,
                            modifier = Modifier
                                .height(15.dp)
                                .padding(horizontal = 5.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(contentColor),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "${todoTask.time?.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                            fontSize = 10.sp,
                            color = containerColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 1.5.em,
                            modifier = Modifier
                                .height(15.dp)
                                .padding(horizontal = 5.dp)
                        )
                    }
                }
            }
        }
    }
}