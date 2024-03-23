package com.example.dayforge.presentation.ui.today

import com.example.dayforge.presentation.models.UiAction

sealed interface TodayUiAction : UiAction {
    data object LoadTodayTasks : TodayUiAction
    data object LoadCategories : TodayUiAction
    data object LoadTomorrowTasks : TodayUiAction
    data class UpdateCurrentTab(val index: Int) : TodayUiAction
    data class CompleteTask(val index: Int) : TodayUiAction
}
