package com.example.data_repository.data_source.local

import com.example.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface LocalCategoryDataSource {

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    fun getAll(): Flow<List<Category>>
}