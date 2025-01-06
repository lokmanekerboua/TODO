package me.lokmvne.core.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import me.lokmvne.core.domain.model.ToDoTask
import java.time.ZoneId
import javax.inject.Inject

const val TODO_TASK_EXTRA = "todoTask"
const val TODO_ID_EXTRA = "todoTaskID"
const val TODO_TITLE_EXTRA = "todoTaskTITLE"
const val TODO_DESC_EXTRA = "todoTaskDESC"

enum class ToDoIntentActions(val action: String) {
    FIRE_ALARM("me.lokmvne.todo.fire_alarm"),
    CANCEL_ALARM("me.lokmvne.todo.cancel_alarm"),
}

class AlarmController @Inject constructor(
    private val alarmManager: AlarmManager,
    @ApplicationContext private val context: Context
) {
    fun setAlarmClock(toDoTask: ToDoTask) {
        val dateTime = toDoTask.date.atTime(toDoTask.time)
        val zoneId = ZoneId.systemDefault()
        val instant = dateTime.atZone(zoneId).toInstant()
        val alarmTime = instant.toEpochMilli()

        val currentTime = System.currentTimeMillis()
        if (currentTime < alarmTime) {
            val alarmIntent = Intent(context, ToDoAlarmReceiver::class.java).apply {
                action = ToDoIntentActions.FIRE_ALARM.action
                putExtra(TODO_ID_EXTRA, toDoTask.id)
                putExtra(TODO_TITLE_EXTRA, toDoTask.title)
                putExtra(TODO_DESC_EXTRA, toDoTask.description)
            }
            val alarmClockPendingIntent = PendingIntent.getBroadcast(
                context,
                toDoTask.id.hashCode(),
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
            alarmManager.cancel(alarmClockPendingIntent)
            val clockInfo = AlarmManager.AlarmClockInfo(
                alarmTime,
                alarmClockPendingIntent
            )
            alarmManager.setAlarmClock(
                clockInfo,
                alarmClockPendingIntent
            )
        }
    }

    //Note: If the PendingIntent was created with FLAG_ONE_SHOT it cannot be canceled.
    fun cancelAlarm(toDoTask: ToDoTask) {
        val alarmIntent = Intent(context, ToDoAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            toDoTask.id.hashCode(),
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}