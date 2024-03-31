package com.example.dayforge.presentation.ui.today

import androidx.compose.runtime.mutableStateListOf
import com.example.dayforge.presentation.models.UiState
import com.example.domain.model.Category
import com.example.domain.model.Task

data class TodayState(
    val search: String = "",
    val todayTasks: List<Task> = mutableStateListOf(),
    val tomorrowTasks: List<Task> = mutableStateListOf(),
    val nextWeekTasks: List<Task> = mutableStateListOf(),
    val categories: List<Category> = mutableStateListOf(),
    val selectedTabIndex: Int = 0,
    override val loading: Boolean = false,
    override val error: Boolean = false,
) : UiState
