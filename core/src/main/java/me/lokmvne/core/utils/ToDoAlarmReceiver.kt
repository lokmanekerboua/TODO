package me.lokmvne.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class ToDoAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @Inject
    lateinit var alarmController: AlarmController

    @Inject
    @ApplicationContext
    lateinit var appcontext: Context

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val todoID = intent?.getLongExtra(TODO_ID_EXTRA, -1L)
        val todoTitle = intent?.getStringExtra(TODO_TITLE_EXTRA)
        val todoDesc = intent?.getStringExtra(TODO_DESC_EXTRA)

        when (action) {
            ToDoIntentActions.FIRE_ALARM.action -> {
                if (todoID != null && todoTitle != null && todoDesc != null) {
                    if (ContextCompat.checkSelfPermission(
                            appcontext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        setNotification(todoID, todoTitle, todoDesc)
                    }
                }
            }

            ToDoIntentActions.CANCEL_ALARM.action -> {
                if (todoID != null && todoTitle != null && todoDesc != null) {
                    cancelNotification(todoID)
                    alarmController.cancelAlarm(todoID)
                    Toast.makeText(context, "Task $todoTitle Reminder Canceled", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            else -> {}
        }


    }

    @SuppressLint("MissingPermission")
    private fun setNotification(taskId: Long, title: String, desc: String) {
        val intent = Intent(appcontext, ToDoAlarmReceiver::class.java).apply {
            action = ToDoIntentActions.CANCEL_ALARM.action
            putExtra(TODO_ID_EXTRA, taskId)
            putExtra(TODO_TITLE_EXTRA, title)
            putExtra(TODO_DESC_EXTRA, desc)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            appcontext,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val cancelAction = NotificationCompat.Action(
            0,
            "Cancel",
            pendingIntent
        )
        val notification = notificationBuilder
            .setContentTitle(title)
            .setContentText(desc)
            .clearActions()
            .addAction(
                cancelAction
            )
            .build()

        notificationManager.notify(taskId.toInt(), notification)
    }

    private fun cancelNotification(todoID: Long) {
        notificationManager.cancel(todoID.toInt())
    }
}