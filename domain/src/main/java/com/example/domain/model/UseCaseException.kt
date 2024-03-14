package com.example.domain.model

sealed class UseCaseException(cause: Throwable) : Throwable(cause) {

    class TaskException(cause: Throwable) : UseCaseException(cause)

    class CategoryException(cause: Throwable) : UseCaseException(cause)

    class UnknownException(cause: Throwable) : UseCaseException(cause)


    companion object {
        fun createFromThrowable(throwable: Throwable): UseCaseException {
            return if (throwable is UseCaseException) throwable else UnknownException(throwable)
        }
    }
}