package com.example.data_local.source

import com.example.data_local.db.task.TaskDao
import com.example.data_local.toTask
import com.example.data_local.toTaskEntity
import com.example.data_repository.data_source.local.LocalTaskDataSource
import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalTaskDataSourceImpl
@Inject
constructor(
    private val taskDao: TaskDao,
) : LocalTaskDataSource {
    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toTaskEntity())
    }

    override fun getAllTasks(): Flow<List<Task>> = flow { taskDao.getAll().map { it.toTask() } }

    override fun getTasksByCategory(categoryId: Long): Flow<List<Task>> =
        flow {
            taskDao
                .getTasksByCategory(
                    categoryId,
                ).map { it.toTask() }
        }

    override fun getTodayTasks(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }
}