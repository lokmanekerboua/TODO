package me.lokmvne.core.di

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.lokmvne.core.utils.Constants.Companion.TODO_NOTIFICATION_CHANNEL_ID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModules {

    @Provides
    @Singleton
    fun provideToDoNotification(@ApplicationContext context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, TODO_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(me.lokmvne.common.R.drawable.logo_light)
            .setPriority(NotificationCompat.PRIORITY_MAX)
    }


    @Provides
    @Singleton
    fun provideToDoNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }

}