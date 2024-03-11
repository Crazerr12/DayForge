package com.example.data_local.db.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)

    @Update
    fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE category_id = :categoryId")
    fun getTasksByCategory(categoryId: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE  start_date =:today")
    fun getTodayTasks(today: LocalDate = LocalDate.now()): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE start_date = :tomorrow")
    fun getTomorrowTasks(tomorrow: LocalDate = LocalDate.now().plusDays(1))
}