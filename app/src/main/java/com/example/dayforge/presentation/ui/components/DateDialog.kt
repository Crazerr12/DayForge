package com.example.dayforge.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dayforge.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate, LocalTime?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var timeDialogIsOpen by remember {
        mutableStateOf(false)
    }

    val dateTime = LocalDate.now()
    val localTime: MutableState<LocalTime?> = remember {
        mutableStateOf(null)
    }

    val datePickerState = remember {
        DatePickerState(
            locale = Locale.getDefault(),
            yearRange = 2023..2030,
            initialSelectedDateMillis = dateTime.atStartOfDay().toInstant(ZoneOffset.UTC)
                .toEpochMilli(),
            initialDisplayMode = DisplayMode.Picker,
            initialDisplayedMonthMillis = null,
        )
    }

    if (timeDialogIsOpen) {
        TimeDialog(onDismiss = { timeDialogIsOpen = false }, onConfirm = {
            localTime.value = LocalTime.of(it.hour, it.minute)
            timeDialogIsOpen = false
        })
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .fillMaxWidth(0.9f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            DatePicker(state = datePickerState)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .clickable {
                        timeDialogIsOpen = true
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_clock_24),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (localTime.value == null) stringResource(id = R.string.choose_a_time)
                    else localTime.value!!.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, end = 6.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(onClick = onDismiss) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                    )
                }

                Button(onClick = {
                    val localDate = datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(
                            it
                        ).atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    onConfirm(localDate!!, localTime.value)
                }) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        }
    }
}

@Preview
@Composable
private fun DateDialogPreview() {
    DateDialog(onDismiss = {}, onConfirm = { _, _ -> })
}