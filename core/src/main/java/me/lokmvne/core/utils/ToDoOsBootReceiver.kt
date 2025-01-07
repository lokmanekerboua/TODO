package me.lokmvne.core.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.lokmvne.core.domain.use_cases.ToDoUseCases
import javax.inject.Inject

@AndroidEntryPoint
class ToDoOsBootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmController: AlarmController

    @Inject
    lateinit var useCases: ToDoUseCases

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            scope.launch {
                useCases.getAllTasksUseCase().collect {
                    it.forEach { task ->
                        alarmController.setAlarmClock(task)
                    }
                }
            }
        }
    }
}