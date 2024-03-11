package com.example.data_repository.repository

import com.example.data_repository.data_source.local.LocalCategoryDataSource
import com.example.domain.model.Category
import com.example.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryDataSource: LocalCategoryDataSource,
) : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> {
        return categoryDataSource.getAll()
    }

    override suspend fun addCategory(category: Category) {
        categoryDataSource.addCategory(category = category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDataSource.deleteCategory(category = category)
    }
}