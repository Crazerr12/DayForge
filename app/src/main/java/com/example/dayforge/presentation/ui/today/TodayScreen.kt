package com.example.dayforge.presentation.ui.today

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayforge.R
import com.example.dayforge.presentation.models.TabItem
import com.example.dayforge.presentation.ui.components.Task
import com.example.dayforge.presentation.ui.utils.CommonScreen
import com.example.domain.model.Category
import com.example.domain.model.Task
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun TodayScreen(
    onTaskClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: TodayViewModel = hiltViewModel(),
) {
    vm.uiState.collectAsState().value.let { state ->
        CommonScreen<TodayState>(state = state) { todayState ->
            TodayContent(
                onTaskClick = onTaskClick,
                state = todayState,
                handleAction = { vm.handleAction(it) },
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayContent(
    state: TodayState,
    handleAction: (TodayUiAction) -> Unit,
    modifier: Modifier = Modifier,
    onTaskClick: () -> Unit,
) {
    val tabItems = listOf(
        TabItem(
            title = R.string.today
        ),
        TabItem(
            title = R.string.tomorrow_tab
        ),
        TabItem(
            title = if (LocalDate.now().dayOfWeek == DayOfWeek.FRIDAY) R.string.next_week_tab
            else R.string.this_week_tab
        ),
    )

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            handleAction(TodayUiAction.UpdateCurrentTab(pagerState.currentPage))
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = state.selectedTabIndex) {
            tabItems.forEachIndexed { index, tab ->
                Tab(
                    selected = index == state.selectedTabIndex,
                    onClick = { handleAction(TodayUiAction.UpdateCurrentTab(index = index)) },
                    text = { Text(text = stringResource(id = tab.title)) },
                )
            }
        }
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            when (index) {
                0 -> {
                    if (state.todayTasks == null) {
                        handleAction(TodayUiAction.LoadTodayTasks)
                    }

                    TaskPage(
                        tasks = state.todayTasks,
                        categories = state.categories,
                        onTaskClick = onTaskClick,
                    )
                }

                1 -> {
                    if (state.tomorrowTasks == null) {
                        handleAction(TodayUiAction.LoadTomorrowTasks)
                    }

                    TaskPage(
                        tasks = state.tomorrowTasks,
                        categories = state.categories,
                        onTaskClick = onTaskClick,
                    )
                }

                2 -> {
                    TaskPage(
                        tasks = state.nextWeekTasks,
                        categories = state.categories,
                        onTaskClick = onTaskClick,
                    )
                }
            }

        }
    }
}

@Composable
fun TaskPage(
    onTaskClick: () -> Unit,
    tasks: List<Task>?,
    categories: List<Category>,
    modifier: Modifier = Modifier,
) {

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(tasks ?: emptyList()) { task ->
            Task(
                title = task.title,
                category = categories.firstOrNull { it.id == task.categoryId },
                startDate = task.startDate,
                timeToComplete = task.timeToComplete,
                timeStart = null,
                onClick = onTaskClick,
            )
        }
    }
}

@Preview
@Composable
private fun TodayContentPreview() {
    TodayContent(state = TodayState(), handleAction = {}, onTaskClick = { })
}