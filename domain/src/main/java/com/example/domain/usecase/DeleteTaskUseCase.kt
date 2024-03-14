package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repositories.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
) {

    suspend fun execute(task: Task) {
        taskRepository.deleteTask(task)
    }
}