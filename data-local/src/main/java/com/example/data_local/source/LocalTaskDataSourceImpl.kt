package com.example.data_local.source

import com.example.data_local.db.task.TaskDao
import com.example.data_local.toTask
import com.example.data_local.toTaskEntity
import com.example.data_local.utils.Converters
import com.example.data_repository.data_source.local.LocalTaskDataSource
import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class LocalTaskDataSourceImpl @Inject constructor(
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

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAll().map { tasks -> tasks.map { it.toTask() } }

    override fun getTask(id: Long): Flow<Task> = taskDao.getTask(id).map { it.toTask() }

    override fun getTasksByCategory(categoryId: Long): Flow<List<Task>> =
        taskDao.getTasksByCategory(categoryId).map { tasks -> tasks.map { it.toTask() } }

    override fun getTodayTasks(): Flow<List<Task>> {
        val todayMillis = Converters().toLocalDate(LocalDate.now())
        return taskDao.getTodayTasks(todayMillis).map { tasks -> tasks.map { it.toTask() } }
    }

    override fun getTomorrowTasks(): Flow<List<Task>> {
        val tomorrowMillis = Converters().toLocalDate(LocalDate.now().plusDays(1))
        return taskDao.getTomorrowTasks(tomorrowMillis).map { tasks -> tasks.map { it.toTask() } }
    }

    override fun getNextOrThisWeekTasks(): Flow<List<Task>> {
        val today = LocalDate.now().dayOfWeek
        val minMillis = Converters().toLocalDate(LocalDate.now().plusDays(2))

        val maxMillis = if (today == DayOfWeek.SATURDAY) Converters().toLocalDate(
            LocalDate.now().plusWeeks(1).with(DayOfWeek.SUNDAY)
        )
        else Converters().toLocalDate(LocalDate.now().with(DayOfWeek.SUNDAY))

        return taskDao.getNextOrThisWeekTasks(minMillis, maxMillis)
            .map { tasks -> tasks.map { it.toTask() } }
    }

    override fun getTasksByCompletionStatus(isComplete: Boolean): Flow<List<Task>> =
        taskDao.getTasksByCompletionStatus(isComplete).map { tasks -> tasks.map { it.toTask() } }
}