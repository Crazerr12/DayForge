package com.example.data_repository.repository

import com.example.data_repository.data_source.local.LocalTaskDataSource
import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val localTaskDataSource: LocalTaskDataSource,
) : TaskRepository {
    override suspend fun addTask(task: Task) {
        localTaskDataSource.addTask(task = task)
    }

    override suspend fun deleteTask(task: Task) {
        localTaskDataSource.deleteTask(task = task)
    }

    override suspend fun updateTask(task: Task) {
        localTaskDataSource.updateTask(task = task)
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return localTaskDataSource.getAllTasks()
    }

    override fun getTask(id: Long): Flow<Task> {
        return localTaskDataSource.getTask(id)
    }

    override fun getTasksByCategory(categoryId: Long): Flow<List<Task>> {
        return localTaskDataSource.getTasksByCategory(categoryId = categoryId)
    }

    override fun getTodayTasks(): Flow<List<Task>> {
        return localTaskDataSource.getTodayTasks()
    }

    override fun getTomorrowTasks(): Flow<List<Task>> {
        return localTaskDataSource.getTomorrowTasks()
    }

    override fun getNextOrThisWeekTasks(): Flow<List<Task>> {
        return localTaskDataSource.getNextOrThisWeekTasks()
    }

    override fun getTasksByCompletionStatus(isComplete: Boolean): Flow<List<Task>> {
        return localTaskDataSource.getTasksByCompletionStatus(isComplete)
    }
}