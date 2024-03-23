package com.example.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val startDate: LocalDate?,
    val timeToComplete: LocalTime?,
    val executionStart: LocalTime?,
    val priority: Priority?,
    val days: List<DayOfWeek>?,
    val categoryId: Long?,
    val isComplete: Boolean,
    val subtasks: List<Subtask>?,
)
