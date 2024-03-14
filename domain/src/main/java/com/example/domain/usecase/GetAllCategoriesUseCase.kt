package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {

    fun execute(): Flow<List<Category>> {
        return categoryRepository.getCategories()
    }
}