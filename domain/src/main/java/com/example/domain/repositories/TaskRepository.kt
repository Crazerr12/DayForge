package com.example.domain.repositories

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    fun getAllTasks(): Flow<List<Task>>

    fun getTasksByCategory(categoryId: Long): Flow<List<Task>>

    fun getTodayTasks(): Flow<List<Task>>
}