package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(
    private val taskRepository: TaskRepository,
) {

    fun execute(): Flow<List<Task>> {
        return taskRepository.getAllTasks()
    }
}