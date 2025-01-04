package me.lokmvne.todo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.HiltAndroidApp
import me.lokmvne.core.utils.Constants.Companion.TODO_NOTIFICATION_CHANNEL_ID
import me.lokmvne.core.utils.Constants.Companion.TODO_NOTIFICATION_CHANNEL_NAME
import javax.inject.Inject

@HiltAndroidApp
class ToDoApp : Application() {

    @Inject
    lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onCreate() {
        super.onCreate()
        val toDoNotificationChannel = NotificationChannel(
            TODO_NOTIFICATION_CHANNEL_ID,
            TODO_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManagerCompat.createNotificationChannel(toDoNotificationChannel)
    }
}