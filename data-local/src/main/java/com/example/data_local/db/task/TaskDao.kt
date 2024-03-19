package com.example.data_local.db.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE category_id = :categoryId")
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: Long): Flow<TaskEntity>

    @Query("SELECT * FROM tasks WHERE  start_date =:today")
    fun getTodayTasks(today: Long?): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE start_date = :tomorrow")
    fun getTomorrowTasks(tomorrow: LocalDate = LocalDate.now().plusDays(1)): Flow<List<TaskEntity>>
}