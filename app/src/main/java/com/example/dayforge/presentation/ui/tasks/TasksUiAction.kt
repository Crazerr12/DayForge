package com.example.dayforge.presentation.ui.tasks

import com.example.dayforge.presentation.models.UiAction

sealed interface TasksUiAction : UiAction {
    data class UpdateCurrentTab(val index: Int) : TasksUiAction
    data class SetSearch(val query: String, val index: Int) : TasksUiAction
    data class CompleteUncompletedTask(val id: Long) : TasksUiAction
    data class CancelCompletedTask(val id: Long) : TasksUiAction
    data class ExpandCategory(val index: Int) : TasksUiAction
    data object UpdateTask : TasksUiAction
    data object LoadCategories : TasksUiAction
    data object LoadUncompletedTasks : TasksUiAction
    data object LoadCompletedTasks : TasksUiAction
}