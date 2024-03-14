package com.example.dayforge.presentation.models

sealed class UiState<out T : Any> {
    data object Loading : UiState<Nothing>()

    data class Success<T : Any>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val errorMessage: String,
    ) : UiState<Nothing>()
}