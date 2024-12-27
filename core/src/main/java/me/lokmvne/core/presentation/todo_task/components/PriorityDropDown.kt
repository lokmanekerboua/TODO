package me.lokmvne.core.presentation.todo_task.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.lokmvne.core.data.utils.Priority

@Composable
fun DropDownMenuItem(priority: Priority, onClick: (priority: Priority) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onClick(priority)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = priority.name,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DropDownIllustrationMenuItem(
    title: String,
    illustration: Int,
    onClick: (title: String, illustration: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onClick(title, illustration)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(id = illustration),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DropDownColorMenuItem(
    title: String,
    color: Long,
    onClick: (title: String, color: Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onClick(title, color)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(2f)
        ) {
            Canvas(
                modifier = Modifier.size(25.dp)
            ) {
                drawCircle(color = Color(color))
            }
        }

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.weight(8f)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}