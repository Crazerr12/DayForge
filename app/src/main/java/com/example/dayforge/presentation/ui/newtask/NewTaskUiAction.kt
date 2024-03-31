package com.example.dayforge.presentation.ui.newtask

import com.example.dayforge.presentation.models.UiAction
import com.example.domain.model.Category
import com.example.domain.model.Priority
import com.example.domain.model.Subtask
import java.time.LocalDate
import java.time.LocalTime

sealed interface NewTaskUiAction : UiAction {
    data object LoadCategories : NewTaskUiAction
    data class UpdateName(val name: String) : NewTaskUiAction
    data class UpdateDescription(val description: String) : NewTaskUiAction
    data class ChooseCategory(val category: Category) : NewTaskUiAction
    data class ExpandCategoryDropdownMenu(val isExpanded: Boolean) : NewTaskUiAction
    data class SetPriority(val priority: Priority) : NewTaskUiAction
    data class ExpandPriorityDropdownMenu(val isExpanded: Boolean) : NewTaskUiAction
    data object AddSubtask : NewTaskUiAction
    data class UpdateSubtask(val subtask: Subtask, val index: Int) : NewTaskUiAction
    data object CreateTask : NewTaskUiAction
    data class ShowHideDateDialog(val isOpen: Boolean) : NewTaskUiAction
    data class OpenTimeDialog(val isOpen: Boolean) : NewTaskUiAction
    data class SetDate(val date: LocalDate, val time: LocalTime?) : NewTaskUiAction
    data class IncompleteCompleteTask(val task: Boolean) : NewTaskUiAction
}