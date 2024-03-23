package com.example.data_local

import com.example.data_local.db.category.CategoryEntity
import com.example.data_local.db.task.TaskEntity
import com.example.domain.model.Category
import com.example.domain.model.Task

internal fun TaskEntity.toTask(): Task =
    Task(
        id = id,
        title = title,
        description = description,
        startDate = startDate,
        timeToComplete = timeToComplete,
        executionStart = executionStart,
        priority = priority,
        days = days,
        categoryId = categoryId,
        isComplete = isComplete,
        subtasks = subtasks
    )

internal fun Task.toTaskEntity(): TaskEntity =
    TaskEntity(
        id = id,
        title = title,
        description = description,
        startDate = startDate,
        timeToComplete = timeToComplete,
        executionStart = executionStart,
        priority = priority,
        days = days,
        categoryId = categoryId,
        isComplete = isComplete,
        subtasks = subtasks
    )

internal fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(id, value)

internal fun CategoryEntity.toCategory(): Category = Category(id, category)