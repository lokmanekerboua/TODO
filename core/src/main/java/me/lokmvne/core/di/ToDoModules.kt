package me.lokmvne.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.lokmvne.core.data.data_source.ToDoDao
import me.lokmvne.core.data.data_source.ToDoDataBase
import me.lokmvne.core.domain.use_cases.AddTaskUseCase
import me.lokmvne.core.domain.use_cases.DeleteAllTasksUseCase
import me.lokmvne.core.domain.use_cases.DeleteTaskUseCase
import me.lokmvne.core.domain.use_cases.GetAllTasksUseCase
import me.lokmvne.core.domain.use_cases.GetSelectedTaskUseCase
import me.lokmvne.core.domain.use_cases.SearchDatabaseUseCase
import me.lokmvne.core.domain.use_cases.ToDoUseCases
import me.lokmvne.core.domain.use_cases.UpdateTaskUseCase
import me.lokmvne.core.repository.ToDoRepository
import me.lokmvne.core.repository.ToDoRepositoryImpl
import me.lokmvne.core.utils.Constants.Companion.TODO_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModules {
    @Singleton
    @Provides
    fun provideTodoDataBase(
        @ApplicationContext context: Context
    ): ToDoDataBase {
        return Room.databaseBuilder(
            context,
            ToDoDataBase::class.java,
            TODO_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoDao(db: ToDoDataBase) = db.toDoDao()

    @Provides
    @Singleton
    fun provideToDoRepository(dao: ToDoDao): ToDoRepository {
        return ToDoRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideToDoUseCases(repository: ToDoRepository): ToDoUseCases {
        return ToDoUseCases(
            addTaskUseCase = AddTaskUseCase(repository),
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            deleteAllTasksUseCase = DeleteAllTasksUseCase(repository),
            getAllTasksUseCase = GetAllTasksUseCase(repository),
            getSelectedTaskUseCase = GetSelectedTaskUseCase(repository),
            searchDatabaseUseCase = SearchDatabaseUseCase(repository),
            updateTaskUseCase = UpdateTaskUseCase(repository)
        )
    }
}