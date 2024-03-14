package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.repositories.CategoryRepository

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {

    suspend fun execute(category: Category) {
        categoryRepository.deleteCategory(category)
    }
}