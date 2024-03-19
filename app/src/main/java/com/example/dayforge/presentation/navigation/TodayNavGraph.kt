package com.example.dayforge.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dayforge.presentation.ui.today.TodayScreen

fun NavGraphBuilder.todayNavGraph(navController: NavController) {
    navigation(startDestination = TodayScreen.Today.route, route = Graph.TODAY) {
        composable(TodayScreen.Today.route) {
            TodayScreen(onTaskClick = { navController.navigate("add_new_task") })
        }
    }
}

sealed class TodayScreen(
    val route: String,
) {
    data object Today : TodayScreen("today")
}