package com.example.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: Long,
    val title: String,
    val startDate: LocalDate?,
    val timeToComplete: LocalTime?,
    val executionStart: LocalTime?,
    val priority: Priority?,
    val days: List<String>?,
    val categoryId: Long,
)
