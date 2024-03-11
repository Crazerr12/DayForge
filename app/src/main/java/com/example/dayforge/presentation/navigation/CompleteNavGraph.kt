package com.example.dayforge.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.completeNavGraph(navController: NavController) {
    navigation(startDestination = CompleteScreen.Complete.route, route = Graph.COMPLETE) {
        composable(CompleteScreen.Complete.route) {
        }
    }
}

sealed class CompleteScreen(
    val route: String,
) {
    data object Complete : CompleteScreen("complete")
}