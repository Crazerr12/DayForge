package com.example.data_repository.data_source.local

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface LocalTaskDataSource {

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    fun getAllTasks(): Flow<List<Task>>

    fun getTask(id: Long): Flow<Task>

    fun getTasksByCategory(categoryId: Long): Flow<List<Task>>

    fun getTodayTasks(): Flow<List<Task>>
}