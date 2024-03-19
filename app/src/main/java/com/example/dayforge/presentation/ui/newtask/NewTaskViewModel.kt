package com.example.dayforge.presentation.ui.newtask

import androidx.lifecycle.viewModelScope
import com.example.dayforge.presentation.base.MviViewModel
import com.example.dayforge.presentation.models.Subtask
import com.example.dayforge.presentation.models.UiState
import com.example.domain.model.Category
import com.example.domain.model.Priority
import com.example.domain.model.Task
import com.example.domain.usecase.AddCategoryUseCase
import com.example.domain.usecase.AddTaskUseCase
import com.example.domain.usecase.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
) : MviViewModel<NewTaskState, UiState<NewTaskState>, NewTaskUiAction>(NewTaskState()) {

    init {
        onLoadCategories()
    }

    override fun initState(): UiState<NewTaskState> = UiState.Success(NewTaskState())

    override fun handleAction(action: NewTaskUiAction) {
        when (action) {
            is NewTaskUiAction.LoadCategories -> onLoadCategories()
            is NewTaskUiAction.UpdateName -> onUpdateName(action.name)
            is NewTaskUiAction.UpdateSubtask -> onUpdateSubtask(action.subtask, action.index)
            is NewTaskUiAction.UpdateDescription -> onUpdateDescription(action.description)
            is NewTaskUiAction.AddSubtask -> onAddSubtask()
            is NewTaskUiAction.ChooseCategory -> onChooseCategory(action.category)
            is NewTaskUiAction.SetPriority -> onSetPriority(action.priority)
            is NewTaskUiAction.CreateTask -> onCreateTask()
            // Dropdown Menu
            is NewTaskUiAction.ExpandCategoryDropdownMenu -> onExpandedDropdownMenu(action.isExpanded)
            is NewTaskUiAction.ExpandPriorityDropdownMenu -> onExpandedPriorityDropdownMenu(action.isExpanded)
            // Dialogs
            is NewTaskUiAction.ShowHideDateDialog -> onOpenDateDialog(action.isOpen)
            is NewTaskUiAction.OpenTimeDialog -> onOpenTimeDialog(action.isOpen)
            // TimeDates
            is NewTaskUiAction.SetDate -> onSetDate(action.date)
            is NewTaskUiAction.SetTime -> onSetTime(action.time)
        }
    }

    private fun onUpdateName(name: String) {
        submitState(state = UiState.Success(uiState.value.getSuccessData().copy(name = name)))
    }

    private fun onUpdateDescription(description: String) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(description = description)
            )
        )
    }

    private fun onAddSubtask() {
        uiState.value.getSuccessData().subtasks.add(Subtask())
    }

    private fun onUpdateSubtask(subtask: Subtask, index: Int) {
        uiState.value.getSuccessData().subtasks[index] = subtask
    }

    private fun onChooseCategory(category: Category) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(
                    category = category,
                    categoryDropDownIsExpanded = category.id.toInt() == 0
                )
            )
        )
    }

    private fun onSetPriority(priority: Priority) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(
                    priority = priority,
                    priorityDropDownIsExpanded = false
                )
            )
        )
    }

    private fun onCreateTask() {
        viewModelScope.launch {
            val state = uiState.value.getSuccessData()

            if (state.category?.id?.toInt() == 0) {
                addCategoryUseCase.execute(state.category)
                val category = state.category.copy(id = state.categories.size.toLong())
                submitState(state = UiState.Success(state.copy(category = category)))
            }

            addTaskUseCase.execute(
                Task(
                    title = state.name,
                    description = state.description,
                    startDate = state.startDate?.toLocalDate(),
                    executionStart = state.startDate?.toLocalTime(),
                    timeToComplete = state.timeToComplete,
                    priority = state.priority,
                    days = state.days,
                    categoryId = state.category?.id,
                )
            )
        }
    }

    private fun onExpandedDropdownMenu(isExpanded: Boolean) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(categoryDropDownIsExpanded = isExpanded)
            )
        )
    }

    private fun onExpandedPriorityDropdownMenu(isExpanded: Boolean) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(priorityDropDownIsExpanded = isExpanded)
            )
        )
    }

    private fun onLoadCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase.execute().collect {
                submitState(
                    state = UiState.Success(
                        uiState.value.getSuccessData().copy(categories = it)
                    )
                )
            }
        }
    }

    private fun onOpenDateDialog(isOpen: Boolean) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(dateDialogIsOpen = !isOpen)
            )
        )
    }

    private fun onOpenTimeDialog(isOpen: Boolean) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(timeDialogIsOpen = !isOpen)
            )
        )
    }

    private fun onSetDate(date: LocalDateTime) {
        submitState(
            state = UiState.Success(
                uiState.value.getSuccessData().copy(startDate = date, dateDialogIsOpen = false)
            )
        )
    }

    private fun onSetTime(time: LocalDateTime) {

    }
}