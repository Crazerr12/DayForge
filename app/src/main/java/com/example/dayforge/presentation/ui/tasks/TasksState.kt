package com.example.dayforge.presentation.ui.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.dayforge.presentation.models.UiState
import com.example.domain.model.Category
import com.example.domain.model.Task

data class TasksState(
    val search: String = "",
    val uncompletedTasksSearch: List<Task> = mutableStateListOf(),
    val completedTasksSearch: List<Task> = mutableStateListOf(),
    val uncompletedTasks: List<Task> = mutableStateListOf(),
    val completedTasks: List<Task> = mutableStateListOf(),
    val categories: SnapshotStateList<Category> = mutableStateListOf(),
    val categoriesIsExpanded: List<Boolean> = List(categories.size) { false },
    val selectedTabIndex: Int = 0,
    override val loading: Boolean = false,
    override val error: Boolean = false,
) : UiState
