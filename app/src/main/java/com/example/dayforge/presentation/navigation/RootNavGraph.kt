package com.example.dayforge.presentation.navigation

import NewTaskScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.TODAY,
        route = Graph.ROOT,
    ) {
        todayNavGraph(navController)
        completeNavGraph(navController)
        tasksNavGraph(navController)
        settingsNavGraph(navController)
        composable(route = "add_new_task") {
            NewTaskScreen(createTask = { navController.navigateUp() })
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val TODAY = "today_graph"
    const val COMPLETE = "complete_graph"
    const val TASKS = "tasks_graph"
    const val SETTINGS = "settings_graph"
}