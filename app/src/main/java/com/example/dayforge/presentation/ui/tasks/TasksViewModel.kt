package com.example.dayforge.presentation.ui.tasks

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.example.dayforge.presentation.base.MviViewModel
import com.example.dayforge.presentation.base.Searcher
import com.example.domain.model.Task
import com.example.domain.usecase.GetAllCategoriesUseCase
import com.example.domain.usecase.GetTasksByCompletionStatusUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel
@Inject
constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTasksByCompletionStatusUseCase: GetTasksByCompletionStatusUseCase,
    private val searcher: Searcher,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : MviViewModel<TasksState, TasksUiAction>(TasksState()) {

    init {
        viewModelScope.launch {
            onLoadUncompletedTasks()
            onLoadCategories()
            onLoadCompletedTasks()
        }
    }

    override fun handleAction(action: TasksUiAction) {
        when (action) {
            is TasksUiAction.UpdateCurrentTab -> onUpdateCurrentTab(action.index)
            is TasksUiAction.UpdateTask -> onUpdateTask()
            is TasksUiAction.LoadUncompletedTasks -> onLoadUncompletedTasks()
            is TasksUiAction.LoadCategories -> onLoadCategories()
            is TasksUiAction.LoadCompletedTasks -> onLoadCompletedTasks()
            is TasksUiAction.CompleteUncompletedTask -> onCompleteUncompletedTask(action.id)
            is TasksUiAction.CancelCompletedTask -> onCancelCompletedTask(action.id)
            is TasksUiAction.ExpandCategory -> onExpandCategory(action.index)
            is TasksUiAction.SetSearch -> {
                when (action.index) {
                    0 -> onSetAllTasksSearch(action.query)
                    1 -> onSetCompletedTasksSearch(action.query)
                }
            }
        }
    }

    private fun onUpdateCurrentTab(index: Int) {
        reduceState { it.copy(selectedTabIndex = index) }
    }

    private fun onLoadCategories() {
        reduceState { it.copy(loading = true) }

        viewModelScope.launch {
            getAllCategoriesUseCase.execute().collect { categories ->
                reduceState {
                    it.copy(
                        categories = categories.toMutableStateList(),
                        loading = false,
                        categoriesIsExpanded = List(categories.size) { false }
                    )
                }
            }
        }
    }

    private fun onLoadUncompletedTasks() {
        reduceState { it.copy(loading = true) }

        viewModelScope.launch {
            getTasksByCompletionStatusUseCase.execute(false).collect { tasks ->
                reduceState {
                    it.copy(
                        uncompletedTasks = tasks,
                        loading = false,
                        uncompletedTasksSearch = tasks
                    )
                }
            }
        }
    }

    private fun onLoadCompletedTasks() {
        reduceState { it.copy(loading = true) }

        viewModelScope.launch {
            getTasksByCompletionStatusUseCase.execute(true).collect { tasks ->
                reduceState {
                    it.copy(
                        completedTasks = tasks,
                        loading = false,
                        completedTasksSearch = tasks
                    )
                }
            }
        }
    }

    private fun onCompleteUncompletedTask(id: Long) {
        val (index, task) = onCompleteTask(id, uiState.value.uncompletedTasks)

        viewModelScope.launch {
            updateTaskUseCase.execute(task)

            reduceState {
                it.copy(
                    uncompletedTasks = changeCompleteTask(
                        index = index,
                        list = uiState.value.uncompletedTasks,
                        task = task
                    )
                )
            }
        }
    }

    private fun onCancelCompletedTask(id: Long) {
        val (index, task) = onCompleteTask(id, uiState.value.completedTasks)

        viewModelScope.launch {
            updateTaskUseCase.execute(task)
        }

        reduceState {
            it.copy(
                uncompletedTasks = changeCompleteTask(
                    index = index,
                    list = uiState.value.completedTasks,
                    task = task
                )
            )
        }
    }

    private fun onExpandCategory(index: Int) {
        reduceState {
            val categories = it.categoriesIsExpanded.toMutableList()
            categories[index] = !categories[index]
            it.copy(categoriesIsExpanded = categories)
        }
    }

    private fun onSetAllTasksSearch(query: String) {
        val tasks =
            searcher.doesMatchSearchQuery(tasks = uiState.value.uncompletedTasks, query = query)
        reduceState { it.copy(search = query, uncompletedTasksSearch = tasks) }
    }

    private fun onSetCompletedTasksSearch(query: String) {
        val tasks =
            searcher.doesMatchSearchQuery(tasks = uiState.value.completedTasks, query = query)
        reduceState { it.copy(search = query, completedTasksSearch = tasks) }
    }

    private fun onUpdateTask() {

    }

    private fun onCompleteTask(id: Long, list: List<Task>): Pair<Int, Task> {
        val task = list.first { it.id == id }
        val index = list.indexOf(task)

        val updatedTask = task.copy(
            isComplete = !task.isComplete
        )

        return Pair(index, updatedTask)
    }

    private fun changeCompleteTask(id: Long, list: List<Task>): List<Task> {
        val tasks = list.toMutableList()
        val (index, task) = onCompleteTask(id, list)

        tasks[index] = task

        return tasks
    }

    private fun changeCompleteTask(list: List<Task>, index: Int, task: Task): List<Task> {
        val tasks = list.toMutableList()
        tasks[index] = task
        return tasks
    }
}