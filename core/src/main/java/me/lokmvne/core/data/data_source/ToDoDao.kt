package me.lokmvne.core.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.lokmvne.core.domain.model.ToDoTask

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_task_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_task_table WHERE id = :taskId")
    fun getSelectedTask(taskId: Long): Flow<ToDoTask>

    @Query("SELECT * FROM todo_task_table WHERE priority = 'HIGH'")
    fun getHighPriorityTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_task_table WHERE date BETWEEN :startDate AND :endDate")
    fun getNearTasks(startDate: Long, endDate: Long): Flow<List<ToDoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(toDoTask: ToDoTask): Long

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_task_table WHERE title like '%'||:searchQuery||'%' OR description LIKE '%'||:searchQuery||'%'")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>
}