package com.example.dayforge.presentation.ui.today

import androidx.lifecycle.viewModelScope
import com.example.dayforge.presentation.base.MviViewModel
import com.example.dayforge.presentation.models.UiState
import com.example.domain.usecase.GetAllCategoriesUseCase
import com.example.domain.usecase.GetTodayTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTodayTasksUseCase: GetTodayTasksUseCase,
) : MviViewModel<TodayState, UiState<TodayState>, TodayUiAction>(TodayState()) {

    init {
        onLoadCategories()
    }

    override fun initState(): UiState<TodayState> = UiState.Loading

    override fun handleAction(action: TodayUiAction) {
        when (action) {
            is TodayUiAction.LoadCategories -> onLoadCategories()
            is TodayUiAction.LoadTodayTasks -> onLoadTodayTasks()
            is TodayUiAction.LoadTomorrowTasks -> onLoadTomorrowTasks()
            is TodayUiAction.UpdateCurrentTab -> onUpdateCurrentTab(action.index)
        }
    }

    private fun onLoadCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase.execute().collect { categories ->
                submitState(
                    state = UiState.Success(
                        TodayState(categories = categories)
                    )
                )
            }
        }
    }

    private fun onLoadTodayTasks() {
        viewModelScope.launch {
            getTodayTasksUseCase.execute().collect { todayTasks ->
                submitState(
                    state = UiState.Success(
                        uiState.value.getSuccessData().copy(
                            todayTasks = todayTasks,
                        )
                    )
                )
            }
        }
    }

    private fun onLoadTomorrowTasks() {
        viewModelScope.launch {

        }
    }

    private fun onUpdateCurrentTab(index: Int) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(selectedTabIndex = index)
            )
        )
    }
}