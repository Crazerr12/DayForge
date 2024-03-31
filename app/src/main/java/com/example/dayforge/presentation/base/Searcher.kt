package com.example.dayforge.presentation.base

import com.example.domain.model.Task

object Searcher {
    fun doesMatchSearchQuery(tasks: List<Task>, query: String): List<Task> {
        return tasks.filter { it.title.contains(query, ignoreCase = true) }
    }
}