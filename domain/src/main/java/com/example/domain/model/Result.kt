package com.example.domain.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    class Error(val exception: UseCaseException) : Result<Nothing>()
}