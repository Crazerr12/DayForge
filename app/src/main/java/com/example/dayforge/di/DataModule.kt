package com.example.dayforge.di

import com.example.data_repository.data_source.local.LocalCategoryDataSource
import com.example.data_repository.data_source.local.LocalTaskDataSource
import com.example.data_repository.repository.CategoryRepositoryImpl
import com.example.data_repository.repository.TaskRepositoryImpl
import com.example.domain.repositories.CategoryRepository
import com.example.domain.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideTaskRepository(localTaskDataSource: LocalTaskDataSource): TaskRepository =
        TaskRepositoryImpl(localTaskDataSource = localTaskDataSource)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDataSource: LocalCategoryDataSource): CategoryRepository =
        CategoryRepositoryImpl(categoryDataSource = categoryDataSource)
}