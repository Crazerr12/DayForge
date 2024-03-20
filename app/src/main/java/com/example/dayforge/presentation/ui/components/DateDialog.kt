package com.example.dayforge.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dayforge.R
import com.example.dayforge.presentation.ui.utils.toMillis
import java.time.LocalDateTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    onDismiss: () -> Unit,
    onConfirm: (DatePickerState) -> Unit,
) {
    var timeDialogIsOpen by remember {
        mutableStateOf(false)
    }

    val dateTime = LocalDateTime.now()

    val datePickerState = remember {
        DatePickerState(
            locale = Locale.getDefault(),
            yearRange = 2023..2030,
            initialSelectedDateMillis = dateTime.toMillis(),
            initialDisplayMode = DisplayMode.Picker,
            initialDisplayedMonthMillis = null,
        )
    }

    if (timeDialogIsOpen) {
        TimePicker(state = rememberTimePickerState())
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {


            DatePicker(state = datePickerState)

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        timeDialogIsOpen = !timeDialogIsOpen
                    },
                value = "",
                onValueChange = {},
                placeholder = { Hint(hint = R.string.specify_the_time) }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(onClick = onDismiss) {
                    Text(text = stringResource(id = R.string.cancel))
                }

                Button(onClick = { onConfirm(datePickerState) }) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DateDialogPreview() {
    DateDialog(onDismiss = {}, onConfirm = {})
}