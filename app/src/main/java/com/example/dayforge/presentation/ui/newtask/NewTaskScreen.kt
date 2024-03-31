import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayforge.R
import com.example.dayforge.presentation.ui.components.DateDialog
import com.example.dayforge.presentation.ui.components.Hint
import com.example.dayforge.presentation.ui.newtask.NewTaskState
import com.example.dayforge.presentation.ui.newtask.NewTaskUiAction
import com.example.dayforge.presentation.ui.newtask.NewTaskViewModel
import com.example.dayforge.presentation.ui.utils.CommonScreen
import com.example.domain.model.Category
import com.example.domain.model.Priority
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewTaskScreen(
    createTask: () -> Unit,
    modifier: Modifier = Modifier,
    vm: NewTaskViewModel = hiltViewModel(),
) {
    vm.uiState.collectAsState().value.let { state ->
        CommonScreen<NewTaskState>(state = state) { newTaskState ->
            NewTaskContent(
                state = newTaskState,
                handleAction = { vm.handleAction(it) },
                createTask = createTask,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskContent(
    state: NewTaskState,
    handleAction: (NewTaskUiAction) -> Unit,
    createTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.dateDialogIsOpen) {
        DateDialog(
            onDismiss = { handleAction(NewTaskUiAction.ShowHideDateDialog(state.dateDialogIsOpen)) },
            onConfirm = { date, time ->
                handleAction(
                    NewTaskUiAction.SetDate(
                        date = date,
                        time = time,
                    )
                )
            },
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.name,
                placeholder = { Hint(hint = R.string.task_name_hint) },
                onValueChange = { handleAction(NewTaskUiAction.UpdateName(it)) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp),
                value = state.description,
                placeholder = { Hint(hint = R.string.task_description_hint) },
                onValueChange = { handleAction(NewTaskUiAction.UpdateDescription(it)) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = state.categoryDropDownIsExpanded,
                onExpandedChange = { handleAction(NewTaskUiAction.ExpandCategoryDropdownMenu(it)) }
            ) {
                TextField(
                    value = state.category?.value ?: "",
                    onValueChange = { handleAction(NewTaskUiAction.ChooseCategory(Category(value = it))) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.categoryDropDownIsExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = state.categoryDropDownIsExpanded,
                    onDismissRequest = { handleAction(NewTaskUiAction.ExpandCategoryDropdownMenu(!state.categoryDropDownIsExpanded)) }) {
                    state.categories.filter {
                        it.value.contains(
                            state.category?.value ?: "",
                            ignoreCase = true
                        )
                    }
                        .forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category.value) },
                                onClick = { handleAction(NewTaskUiAction.ChooseCategory(category)) })
                        }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.priority))

                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            handleAction(NewTaskUiAction.ExpandPriorityDropdownMenu(!state.priorityDropDownIsExpanded))
                        },
                        text = state.priority.value
                    )

                    DropdownMenu(
                        expanded = state.priorityDropDownIsExpanded,
                        onDismissRequest = {
                            handleAction(
                                NewTaskUiAction.ExpandPriorityDropdownMenu(
                                    !state.priorityDropDownIsExpanded
                                )
                            )
                        }) {

                        Priority.entries.forEach { priorityItem ->
                            DropdownMenuItem(
                                text = { Text(text = priorityItem.value) },
                                onClick = { handleAction(NewTaskUiAction.SetPriority(priorityItem)) })
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.subtasks),
                    style = MaterialTheme.typography.bodyMedium
                )

                IconButton(onClick = { handleAction(NewTaskUiAction.AddSubtask) }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = stringResource(id = R.string.add_subtask)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        itemsIndexed(state.subtasks) { index, subtask ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = subtask.isComplete,
                    onCheckedChange = {
                        handleAction(
                            NewTaskUiAction.UpdateSubtask(
                                subtask = subtask,
                                index = index,
                            )
                        )
                    })
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        handleAction(NewTaskUiAction.ShowHideDateDialog(state.dateDialogIsOpen))
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.period_of_execution))

                Text(
                    text = if (state.startDate == null) stringResource(id = R.string.choose_a_day)
                    else state.startDate.format(
                        DateTimeFormatter.ofPattern(
                            "EEEE, dd MMMM yyyy, HH:mm",
                            Locale.getDefault()
                        )
                    )
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    handleAction(NewTaskUiAction.CreateTask)
                    createTask()
                }
            ) {
                Text(text = stringResource(R.string.create_task))
            }
        }
    }
}

@Preview
@Composable
private fun NewTaskContentPreview() {
    NewTaskContent(state = NewTaskState(), handleAction = {}, createTask = {})
}