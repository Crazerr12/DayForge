package com.example.dayforge.presentation.ui.newtask

import androidx.lifecycle.viewModelScope
import com.example.dayforge.presentation.base.MviViewModel
import com.example.domain.model.Category
import com.example.domain.model.Priority
import com.example.domain.model.Subtask
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
) : MviViewModel<NewTaskState, NewTaskUiAction>(NewTaskState()) {

    init {
        onLoadCategories()
    }

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
            is NewTaskUiAction.IncompleteCompleteTask -> onCompleteTask(action.task)
        }
    }

    private fun onUpdateName(name: String) {
        reduceState { it.copy(name = name) }
    }

    private fun onUpdateDescription(description: String) {
        reduceState { it.copy(description = description) }
    }

    private fun onAddSubtask() {
        uiState.value.subtasks.add(Subtask(name = "", isComplete = false))
    }

    private fun onUpdateSubtask(subtask: Subtask, index: Int) {
        uiState.value.subtasks[index] = subtask
    }

    private fun onChooseCategory(category: Category) {
        reduceState {
            it.copy(
                category = category,
                categoryDropDownIsExpanded = category.id.toInt() == 0
            )
        }
    }

    private fun onSetPriority(priority: Priority) {
        reduceState {
            it.copy(
                priority = priority,
                priorityDropDownIsExpanded = false
            )
        }
    }

    private fun onCreateTask() {
        viewModelScope.launch {
            val state = uiState.value

            if (state.category?.id?.toInt() == 0) {
                addCategoryUseCase.execute(state.category)
                val category = state.category.copy(id = state.categories.size.toLong())
                reduceState { it.copy(category = category) }
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
                    isComplete = state.isComplete,
                    subtasks = state.subtasks
                )
            )
        }
    }

    private fun onExpandedDropdownMenu(isExpanded: Boolean) {
        reduceState { it.copy(categoryDropDownIsExpanded = isExpanded) }
    }

    private fun onExpandedPriorityDropdownMenu(isExpanded: Boolean) {
        reduceState { it.copy(priorityDropDownIsExpanded = isExpanded) }
    }

    private fun onLoadCategories() {
        reduceState { it.copy(loading = true) }
        viewModelScope.launch {
            getAllCategoriesUseCase.execute().collect { categories ->
                reduceState { it.copy(categories = categories, loading = false) }
            }
        }
    }

    private fun onOpenDateDialog(isOpen: Boolean) {
        reduceState { it.copy(dateDialogIsOpen = !isOpen) }
    }

    private fun onOpenTimeDialog(isOpen: Boolean) {
        reduceState { it.copy(timeDialogIsOpen = !isOpen) }
    }

    private fun onSetDate(date: LocalDateTime) {
        reduceState { it.copy(startDate = date, dateDialogIsOpen = false) }
    }

    private fun onSetTime(time: LocalDateTime) {

    }

    private fun onCompleteTask(isComplete: Boolean) {
        reduceState { it.copy(isComplete = !isComplete) }
    }
}