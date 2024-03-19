package com.example.dayforge.presentation.ui.utils

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()