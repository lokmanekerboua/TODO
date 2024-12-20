package me.lokmvne.core.data.utils

import androidx.compose.ui.graphics.Color

enum class Priority(val color: Color) {
    NONE(nonePriorityColor),
    LOW(lowPriorityColor),
    MEDIUM(mediumPriorityColor),
    HIGH(highPriorityColor)
}

val highPriorityColor = Color(0xFFEC0000)
val mediumPriorityColor = Color(0xFFCE6A00)
val lowPriorityColor = Color(0xFF117D26)
val nonePriorityColor = Color(0xFF727272)
