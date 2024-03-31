package com.example.dayforge.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dayforge.R
import com.example.dayforge.presentation.ui.utils.DateFormatterUtil
import com.example.domain.model.Category
import com.example.domain.model.Subtask
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.TextFlowObstacleAlignment
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task(
    title: String,
    description: String?,
    isComplete: Boolean,
    category: Category?,
    dateStart: LocalDate?,
    timeToComplete: LocalTime?,
    subtasks: List<Subtask>?,
    timeStart: LocalTime?,
    onClick: () -> Unit,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isSubtasksNow by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)) {
                    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                        RadioButton(
                            selected = isComplete,
                            onClick = onCompleteClick
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    TextFlow(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        obstacleAlignment = TextFlowObstacleAlignment.TopEnd,
                        obstacleContent = {
                            if (subtasks?.isNotEmpty() == true) {
                                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                    Switch(
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                            .size(width = 50.dp, height = 25.dp)
                                            .scale(0.8f),
                                        checked = isSubtasksNow,
                                        onCheckedChange = { isSubtasksNow = it },
                                    )
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    if (isSubtasksNow) {
                        Column {
                            subtasks?.forEach() { subtask ->
                                Text(text = subtask.name)
                            }
                        }
                    } else {
                        description?.let {
                            Text(
                                text = it,
                            )
                        }
                    }
                }
            }

            if (dateStart != null || timeToComplete != null) {
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dateStart != null) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = DateFormatterUtil.formatDateWithDayOfWeekToYearNumberMonth(
                                dateStart = dateStart,
                                timeStart = timeStart
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    if (category != null) {
                        Text(text = category.value)

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    if (timeToComplete != null) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_clock_24),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = timeToComplete.format(DateTimeFormatter.ofPattern("HH:mm")),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskPreviewWithLargeTitle() {
    Task(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = LocalTime.now(),
        dateStart = LocalDate.now(),
        onClick = {},
        onCompleteClick = {},
        subtasks = listOf(Subtask(name = "", isComplete = false))
    )
}

@Preview
@Composable
private fun TaskPreviewWithSmallTitle() {
    Task(
        title = "Lorem",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = LocalTime.now(),
        dateStart = LocalDate.now(),
        onClick = {},
        onCompleteClick = {},
        subtasks = listOf(Subtask(name = "", isComplete = false))
    )
}

@Preview
@Composable
private fun TaskPreviewWithoutSubtasks() {
    Task(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = LocalTime.now(),
        dateStart = LocalDate.now(),
        onClick = {},
        onCompleteClick = {},
        subtasks = emptyList()
    )
}

@Preview
@Composable
private fun TaskPreviewWithoutTimeStart() {
    Task(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = null,
        dateStart = LocalDate.now(),
        onClick = {},
        onCompleteClick = {},
        subtasks = listOf(Subtask(name = "", isComplete = false))
    )
}

@Preview
@Composable
private fun TaskPreviewWithoutStartDate() {
    Task(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = LocalTime.now(),
        dateStart = null,
        onClick = {},
        onCompleteClick = {},
        subtasks = listOf(Subtask(name = "", isComplete = false))
    )
}

@Preview
@Composable
private fun TaskPreviewWithoutTimeToComplete() {
    Task(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = null,
        timeStart = LocalTime.now(),
        dateStart = LocalDate.now(),
        onClick = {},
        onCompleteClick = {},
        subtasks = listOf(Subtask(name = "", isComplete = false))
    )
}

@Preview
@Composable
private fun TaskPreviewWithoutTime() {
    Task(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sit amet ornare mauris. Donec lacus magna, pulvinar porta commodo vel.",
        isComplete = false,
        category = Category(1, "Work"),
        timeToComplete = null,
        timeStart = null,
        dateStart = null,
        onClick = {},
        onCompleteClick = {},
        subtasks = listOf(Subtask(name = "", isComplete = false))
    )
}