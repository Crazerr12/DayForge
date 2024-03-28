package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksByCompletionStatusUseCase(
    private val taskRepository: TaskRepository,
) {

    fun execute(isComplete: Boolean): Flow<List<Task>> {
        return taskRepository.getTasksByCompletionStatus(isComplete = isComplete)
    }
}