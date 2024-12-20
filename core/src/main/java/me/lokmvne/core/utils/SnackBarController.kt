package me.lokmvne.core.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarAction(
    val name: String,
    val action: () -> Unit
)

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null
)

object SnackBarController {
    private val _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()
    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }
}