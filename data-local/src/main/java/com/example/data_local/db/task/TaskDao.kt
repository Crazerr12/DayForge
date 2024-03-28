package com.example.data_local.db.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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
    fun getTomorrowTasks(tomorrow: Long?): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE start_date BETWEEN :minDate AND :maxDate")
    fun getNextOrThisWeekTasks(minDate: Long?, maxDate: Long?): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE is_complete = :isComplete")
    fun getTasksByCompletionStatus(isComplete: Boolean): Flow<List<TaskEntity>>
}