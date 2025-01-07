package me.lokmvne.core.presentation.todo_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.lokmvne.compose.ui.theme.ToDoAppTheme
import me.lokmvne.common.utils.OrderType
import me.lokmvne.common.utils.ToDoOrder
import me.lokmvne.core.R

@Composable
fun OrderingSection(
    modifier: Modifier = Modifier,
    taskOrder: ToDoOrder,
    onTaskOrderChange: (ToDoOrder) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ToDoRadioButton(
                text = stringResource(R.string.order_asc),
                isSelect = taskOrder.orderType is OrderType.Ascending,
                onClick = {
                    onTaskOrderChange(taskOrder.copy(orderType = OrderType.Ascending))
                }
            )

            ToDoRadioButton(
                text = stringResource(R.string.order_desc),
                isSelect = taskOrder.orderType is OrderType.Descending,
                onClick = {
                    onTaskOrderChange(taskOrder.copy(orderType = OrderType.Descending))
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ToDoRadioButton(
                text = stringResource(R.string.order_title),
                isSelect = taskOrder is ToDoOrder.Title,
                onClick = {
                    onTaskOrderChange(ToDoOrder.Title(taskOrder.orderType))
                }
            )

            ToDoRadioButton(
                text = stringResource(R.string.order_priority),
                isSelect = taskOrder is ToDoOrder.Priority,
                onClick = {
                    onTaskOrderChange(ToDoOrder.Priority(taskOrder.orderType))
                }
            )

            ToDoRadioButton(
                text = stringResource(R.string.order_date),
                isSelect = taskOrder is ToDoOrder.TriggerTime,
                onClick = {
                    onTaskOrderChange(ToDoOrder.TriggerTime(taskOrder.orderType))
                }
            )
        }
    }
}

@Composable
fun ToDoRadioButton(
    text: String,
    isSelect: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = isSelect,
            onClick = { onClick() }
        )
        Spacer(modifier = Modifier)
        Text(text)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OrderingSectionPreview() {
    ToDoAppTheme {
        OrderingSection(
            modifier = Modifier.height(100.dp),
            taskOrder = ToDoOrder.Priority(OrderType.Descending),
            onTaskOrderChange = {}
        )
    }
}