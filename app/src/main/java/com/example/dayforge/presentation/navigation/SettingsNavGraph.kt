package com.example.dayforge.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.settingsNavGraph(navController: NavController) {
    navigation(startDestination = SettingsScreen.Settings.route, route = Graph.SETTINGS) {
        composable(SettingsScreen.Settings.route) {
        }
    }
}

sealed class SettingsScreen(
    val route: String,
) {
    data object Settings : SettingsScreen("settings")

    data object Report : SettingsScreen("report")
}