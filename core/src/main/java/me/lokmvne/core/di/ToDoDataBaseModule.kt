package me.lokmvne.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.lokmvne.core.data.models.ToDoDataBase
import me.lokmvne.core.utils.Constants.Companion.TODO_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoDataBaseModule {
    @Singleton
    @Provides
    fun provideTodoDataBase(
        @ApplicationContext context: Context
    ): RoomDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDataBase::class.java,
            TODO_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoDao(db: ToDoDataBase) = db.toDoDao()

}