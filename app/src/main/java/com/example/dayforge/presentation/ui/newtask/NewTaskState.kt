package com.example.dayforge.presentation.ui.newtask

import androidx.compose.runtime.mutableStateListOf
import com.example.dayforge.presentation.models.UiState
import com.example.domain.model.Category
import com.example.domain.model.Priority
import com.example.domain.model.Subtask
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class NewTaskState(
    val name: String = "",
    val description: String = "",
    val categoryDropDownIsExpanded: Boolean = false,
    val categories: List<Category> = emptyList(),
    val category: Category? = null,
    val priority: Priority = Priority.NONE,
    val priorityDropDownIsExpanded: Boolean = false,
    val subtasks: MutableList<Subtask> = mutableStateListOf(),
    val repeat: Boolean = false,
    val days: MutableList<DayOfWeek>? = mutableStateListOf(),
    val startDate: LocalDate? = null,
    val startTime: LocalTime? = null,
    val timeToComplete: LocalTime? = null,
    val dateDialogIsOpen: Boolean = false,
    val timeDialogIsOpen: Boolean = false,
    val isComplete: Boolean = false,
    override val loading: Boolean = false,
    override val error: Boolean = false,
) : UiState
