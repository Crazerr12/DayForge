package com.example.dayforge.di

import com.example.domain.repositories.CategoryRepository
import com.example.domain.repositories.TaskRepository
import com.example.domain.usecase.AddCategoryUseCase
import com.example.domain.usecase.AddTaskUseCase
import com.example.domain.usecase.DeleteCategoryUseCase
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllCategoriesUseCase
import com.example.domain.usecase.GetAllTasksUseCase
import com.example.domain.usecase.GetTasksByCategoryUseCase
import com.example.domain.usecase.GetTodayTasksUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideAddCategoryUseCase(categoryRepository: CategoryRepository): AddCategoryUseCase =
        AddCategoryUseCase(categoryRepository = categoryRepository)

    @Provides
    fun provideAddTaskUseCase(taskRepository: TaskRepository): AddTaskUseCase =
        AddTaskUseCase(taskRepository = taskRepository)

    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository): DeleteTaskUseCase =
        DeleteTaskUseCase(taskRepository = taskRepository)

    @Provides
    fun provideDeleteCategoryUseCase(categoryRepository: CategoryRepository): DeleteCategoryUseCase =
        DeleteCategoryUseCase(categoryRepository = categoryRepository)

    @Provides
    fun provideGetAllTasksUseCase(taskRepository: TaskRepository): GetAllTasksUseCase =
        GetAllTasksUseCase(taskRepository = taskRepository)

    @Provides
    fun provideGetAllCategoriesUseCase(categoryRepository: CategoryRepository): GetAllCategoriesUseCase =
        GetAllCategoriesUseCase(categoryRepository = categoryRepository)

    @Provides
    fun provideUpdateTasksUseCase(taskRepository: TaskRepository): UpdateTaskUseCase =
        UpdateTaskUseCase(taskRepository = taskRepository)

    @Provides
    fun provideGetTodayTasksUseCase(taskRepository: TaskRepository): GetTodayTasksUseCase =
        GetTodayTasksUseCase(taskRepository = taskRepository)

    @Provides
    fun provideGetTasksByCategoriesUseCase(taskRepository: TaskRepository): GetTasksByCategoryUseCase =
        GetTasksByCategoryUseCase(taskRepository = taskRepository)
}