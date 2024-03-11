package com.example.data_local.di

import com.example.data_local.source.LocalCategoryDataSourceImpl
import com.example.data_local.source.LocalTaskDataSourceImpl
import com.example.data_repository.data_source.local.LocalCategoryDataSource
import com.example.data_repository.data_source.local.LocalTaskDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindCategoryDataSource(categoryDataSource: LocalCategoryDataSourceImpl): LocalCategoryDataSource

    @Binds
    @Singleton
    abstract fun bindTaskDataSource(taskDataSource: LocalTaskDataSourceImpl): LocalTaskDataSource
}