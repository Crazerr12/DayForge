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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
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
import com.example.dayforge.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialog(
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState) -> Unit,
    modifier: Modifier = Modifier,
) {

    val localTime = LocalTime.now()

    var isTimePicker by remember {
        mutableStateOf(true)
    }
    val timePickerState = rememberTimePickerState(
        initialHour = localTime.hour,
        initialMinute = localTime.minute,
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.choose_a_time),
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isTimePicker) {
                    TimePicker(
                        state = timePickerState,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    Row(modifier = Modifier.fillMaxWidth()) {
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = { isTimePicker = !isTimePicker },
                        modifier = Modifier.then(Modifier.size(24.dp))
                    ) {
                        if (isTimePicker) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_keyboard_24),
                                contentDescription = ""
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_clock_24),
                                contentDescription = ""
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(onClick = { onConfirm(timePickerState) }) {
                        Text(text = stringResource(R.string.okay))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TimeDialogPreview() {
    TimeDialog(onDismiss = { }, onConfirm = {})
}