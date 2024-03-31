import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayforge.R
import com.example.dayforge.presentation.models.TabItem
import com.example.dayforge.presentation.ui.components.CategoryHeader
import com.example.dayforge.presentation.ui.components.Hint
import com.example.dayforge.presentation.ui.components.Task
import com.example.dayforge.presentation.ui.tasks.TasksState
import com.example.dayforge.presentation.ui.tasks.TasksUiAction
import com.example.dayforge.presentation.ui.tasks.TasksViewModel
import com.example.dayforge.presentation.ui.utils.CommonScreen
import com.example.domain.model.Category
import com.example.domain.model.Priority
import com.example.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TasksScreen(
    onTaskClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: TasksViewModel = hiltViewModel(),
) {
    vm.uiState.collectAsState().value.let { tasksState ->
        CommonScreen<TasksState>(state = tasksState) { state ->
            TasksContent(
                onTaskClick = onTaskClick,
                state = state,
                handleAction = { vm.handleAction(it) },
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksContent(
    onTaskClick: () -> Unit,
    state: TasksState,
    handleAction: (TasksUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(TabItem(title = R.string.all), TabItem(title = R.string.complete))

    val pagerState = rememberPagerState {
        tabs.size
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            handleAction(TasksUiAction.UpdateCurrentTab(pagerState.currentPage))
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.search,
            onValueChange = { handleAction(TasksUiAction.SetSearch(it, state.selectedTabIndex)) },
            placeholder = { Hint(hint = androidx.appcompat.R.string.abc_search_hint) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            trailingIcon = {
                if (state.search.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable {
                            handleAction(TasksUiAction.SetSearch("", state.selectedTabIndex))
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            }
        )

        TabRow(selectedTabIndex = state.selectedTabIndex) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == state.selectedTabIndex,
                    onClick = { handleAction(TasksUiAction.UpdateCurrentTab(index = index)) },
                    text = {
                        Text(text = stringResource(tabItem.title))
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            when (index) {
                0 -> TasksPage(
                    tasks = state.uncompletedTasksSearch,
                    categories = state.categories,
                    onTaskClick = onTaskClick,
                    onCompleteClick = { handleAction(TasksUiAction.CompleteUncompletedTask(it)) },
                    categoriesIsExpanded = state.categoriesIsExpanded,
                    onExpandCategory = { handleAction(TasksUiAction.ExpandCategory(it)) },
                )

                1 -> {
                    val completedCategories = state.completedTasksSearch.map { it.categoryId }
                    val categories = state.categories.filter { category ->
                        completedCategories.contains(category.id)
                    }

                    TasksPage(
                        tasks = state.completedTasksSearch,
                        categories = categories,
                        onTaskClick = onTaskClick,
                        onCompleteClick = { handleAction(TasksUiAction.CancelCompletedTask(it)) },
                        categoriesIsExpanded = state.categoriesIsExpanded,
                        onExpandCategory = { handleAction(TasksUiAction.ExpandCategory(it)) },
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksPage(
    onTaskClick: () -> Unit,
    onCompleteClick: (Long) -> Unit,
    onExpandCategory: (Int) -> Unit,
    tasks: List<Task>,
    categories: List<Category>,
    categoriesIsExpanded: List<Boolean>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        categories.forEachIndexed { indexCategory, category ->
            stickyHeader {
                CategoryHeader(
                    header = category.value.ifEmpty { stringResource(id = R.string.without_category) },
                    isExpand = categoriesIsExpanded[indexCategory],
                    onClick = { onExpandCategory(indexCategory) }
                )
            }

            if (categoriesIsExpanded[indexCategory]) {
                items(tasks.filter { it.categoryId == category.id }) { task ->
                    Spacer(modifier = Modifier.height(10.dp))

                    Task(
                        title = task.title,
                        description = task.description,
                        isComplete = task.isComplete,
                        category = null,
                        dateStart = task.startDate,
                        timeToComplete = task.timeToComplete,
                        subtasks = task.subtasks,
                        timeStart = task.executionStart,
                        onClick = onTaskClick,
                        onCompleteClick = { onCompleteClick(task.id) },
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TasksScreenPreview() {
    TasksContent(
        onTaskClick = {},
        state = TasksState(
            search = "",
            uncompletedTasksSearch = listOf(
                Task(
                    title = "First Task",
                    description = "Description",
                    startDate = LocalDate.now(),
                    timeToComplete = LocalTime.now(),
                    executionStart = null,
                    priority = Priority.NORMAL,
                    days = emptyList(),
                    categoryId = 1,
                    isComplete = false,
                    subtasks = emptyList(),
                ),
                Task(
                    title = "Second Task",
                    description = "Description",
                    startDate = LocalDate.now(),
                    timeToComplete = LocalTime.now(),
                    executionStart = null,
                    priority = Priority.NORMAL,
                    days = emptyList(),
                    categoryId = 2,
                    isComplete = false,
                    subtasks = emptyList(),
                )
            ),
            completedTasksSearch = emptyList(),
            uncompletedTasks = listOf(
                Task(
                    title = "First Task",
                    description = "Description",
                    startDate = LocalDate.now(),
                    timeToComplete = LocalTime.now(),
                    executionStart = null,
                    priority = Priority.NORMAL,
                    days = emptyList(),
                    categoryId = 1,
                    isComplete = false,
                    subtasks = emptyList(),
                ),
                Task(
                    title = "Second Task",
                    description = "Description",
                    startDate = LocalDate.now(),
                    timeToComplete = LocalTime.now(),
                    executionStart = null,
                    priority = Priority.NORMAL,
                    days = emptyList(),
                    categoryId = 2,
                    isComplete = false,
                    subtasks = emptyList(),
                )
            ),
            completedTasks = listOf(
                Task(
                    title = " First Task",
                    description = "Description",
                    startDate = LocalDate.now(),
                    timeToComplete = LocalTime.now(),
                    executionStart = null,
                    priority = Priority.NORMAL,
                    days = emptyList(),
                    categoryId = 1,
                    isComplete = false,
                    subtasks = emptyList(),
                )
            ),
            categories = listOf(
                Category(id = 1, value = "Work"),
                Category(id = 2, value = "Home")
            ).toMutableStateList(),
            categoriesIsExpanded = listOf(true, false),
            selectedTabIndex = 0,
            loading = false,
            error = false,
        ),
        handleAction = {}
    )
}