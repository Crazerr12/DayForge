package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.repositories.CategoryRepository

class AddCategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {

    suspend fun execute(category: Category) {
        categoryRepository.addCategory(category)
    }
}