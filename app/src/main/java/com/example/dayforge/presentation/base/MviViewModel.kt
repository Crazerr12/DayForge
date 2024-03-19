package com.example.dayforge.presentation.base

import androidx.lifecycle.ViewModel
import com.example.dayforge.presentation.models.UiAction
import com.example.dayforge.presentation.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class MviViewModel<S : UiState, A : UiAction>(
    initialState: S,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState

    abstract fun handleAction(action: A)


    protected fun reduceState(reducer: (S) -> S) {
        _uiState.value = reducer(_uiState.value)
    }
}