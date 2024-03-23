package com.example.dayforge.presentation.ui.today

import androidx.lifecycle.viewModelScope
import com.example.dayforge.presentation.base.MviViewModel
import com.example.domain.usecase.GetAllCategoriesUseCase
import com.example.domain.usecase.GetTodayTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTodayTasksUseCase: GetTodayTasksUseCase,
) : MviViewModel<TodayState, TodayUiAction>(TodayState()) {

    init {
        onLoadCategories()
    }

    override fun handleAction(action: TodayUiAction) {
        when (action) {
            is TodayUiAction.LoadCategories -> onLoadCategories()
            is TodayUiAction.LoadTodayTasks -> onLoadTodayTasks()
            is TodayUiAction.LoadTomorrowTasks -> onLoadTomorrowTasks()
            is TodayUiAction.UpdateCurrentTab -> onUpdateCurrentTab(action.index)
            is TodayUiAction.CompleteTask -> onCompleteTask(action.index)
        }
    }

    private fun onLoadCategories() {
        reduceState { it.copy(loading = true) }
        viewModelScope.launch {
            getAllCategoriesUseCase.execute().collect { categories ->
                reduceState { it.copy(categories = categories, loading = false) }
            }
        }
    }

    private fun onLoadTodayTasks() {
        reduceState { it.copy(loading = false) }
        viewModelScope.launch {
            getTodayTasksUseCase.execute().collect { todayTasks ->
                reduceState { it.copy(todayTasks = todayTasks, loading = false) }
            }
        }
    }

    private fun onLoadTomorrowTasks() {
        viewModelScope.launch {

        }
    }

    private fun onUpdateCurrentTab(index: Int) {
        reduceState { it.copy(selectedTabIndex = index) }
    }

    private fun onCompleteTask(index: Int) {
        val tasks = uiState.value.todayTasks.toMutableList()
        val task = tasks[index]

        tasks[index] = task.copy(
            isComplete = !task.isComplete
        )

        reduceState { it.copy(todayTasks = tasks) }
    }
}