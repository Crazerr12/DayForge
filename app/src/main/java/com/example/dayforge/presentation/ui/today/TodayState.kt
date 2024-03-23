package com.example.dayforge.presentation.ui.today

import androidx.compose.runtime.mutableStateListOf
import com.example.dayforge.presentation.models.UiState
import com.example.domain.model.Category
import com.example.domain.model.Task

data class TodayState(
    val search: String = "",
    val todayTasks: List<Task> = emptyList(),
    val tomorrowTasks: List<Task> = emptyList(),
    val nextWeekTasks: List<Task> = emptyList(),
    val categories: List<Category> = mutableStateListOf(),
    val selectedTabIndex: Int = 0,
    override val loading: Boolean = false,
    override val error: Boolean = false,
) : UiState
