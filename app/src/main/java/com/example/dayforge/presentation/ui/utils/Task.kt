package com.example.dayforge.presentation.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dayforge.R
import com.example.domain.model.Category
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun Task(
    title: String,
    category: Category,
    startDate: LocalDate?,
    timeToComplete: LocalTime?,
    timeStart: LocalTime?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        onClick = { /*TODO*/ },
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_today_24),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(5.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Text(text = title)

                    if (timeToComplete != null) {
                        Spacer(modifier = Modifier.width(10.dp))

                        val minutes = timeToComplete.minute
                        val pluralSuffix = if (minutes == 1) "" else "s"

                        Text(text = "$minutes minute$pluralSuffix")
                    }
                }

                Row {
                    Text(text = category.category)

                    if (timeStart != null) {
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = "Due on ${timeStart.hour}:${timeStart.minute}")
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Preview
@Composable
private fun TaskPreviewWithoutTimes() {
    Task(
        title = "Call with Dr. Smith",
        category = Category(1, "Work"),
        timeToComplete = null,
        timeStart = null,
        startDate = LocalDate.now(),
    )
}

@Preview
@Composable
private fun TaskPreviewWithTimeToComplete() {
    Task(
        title = "Call with Dr. Smith",
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = null,
        startDate = LocalDate.now(),
    )
}

@Preview
@Composable
private fun TaskPreviewWithStartTime() {
    Task(
        title = "Call with Dr. Smith",
        category = Category(1, "Work"),
        timeToComplete = null,
        timeStart = LocalTime.now(),
        startDate = LocalDate.now(),
    )
}

@Preview
@Composable
private fun TaskPreviewWithBothTimes() {
    Task(
        title = "Call with Dr. Smith",
        category = Category(1, "Work"),
        timeToComplete = LocalTime.now(),
        timeStart = LocalTime.now(),
        startDate = LocalDate.now(),
    )
}