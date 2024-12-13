package me.lokmvne.core.data.models

import androidx.compose.ui.graphics.Color

enum class Priority(val color: Color) {
    HIGH(highPriorityColor),
    MEDIUM(mediumPriorityColor),
    LOW(lowPriorityColor),
    NONE(nonePriorityColor)
}

val highPriorityColor = Color(0xFFEC0000)
val mediumPriorityColor = Color(0xFFCE6A00)
val lowPriorityColor = Color(0xFF42F800)
val nonePriorityColor = Color(0xFF727272)
