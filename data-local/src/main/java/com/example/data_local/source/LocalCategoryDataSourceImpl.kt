package com.example.data_local.source

import com.example.data_local.db.category.CategoryDao
import com.example.data_local.toCategory
import com.example.data_local.toCategoryEntity
import com.example.data_repository.data_source.local.LocalCategoryDataSource
import com.example.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalCategoryDataSourceImpl
@Inject
constructor(
    private val categoryDao: CategoryDao,
) : LocalCategoryDataSource {
    override suspend fun addCategory(category: Category) {
        categoryDao.insertCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

    override fun getAll(): Flow<List<Category>> =
        flow { categoryDao.getAll().map { it.toCategory() } }
}