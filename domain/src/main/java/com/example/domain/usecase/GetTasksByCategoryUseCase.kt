package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksByCategoryUseCase(
    private val taskRepository: TaskRepository,
) {

    fun execute(categoryId: Long): Flow<List<Task>> {
        return taskRepository.getTasksByCategory(categoryId)
    }
}