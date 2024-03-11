package com.example.dayforge.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.tasksNavGraph(navController: NavController) {
    navigation(startDestination = TasksScreen.Tasks.route, route = Graph.TASKS) {
        composable(TasksScreen.Tasks.route) {
        }
    }
}

sealed class TasksScreen(
    val route: String,
) {
    data object Tasks : TasksScreen("tasks")
}