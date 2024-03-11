package com.example.dayforge.presentation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.dayforge.R
import com.example.dayforge.presentation.navigation.CompleteScreen
import com.example.dayforge.presentation.navigation.Graph
import com.example.dayforge.presentation.navigation.SettingsScreen
import com.example.dayforge.presentation.navigation.TasksScreen
import com.example.dayforge.presentation.navigation.TodayScreen

enum class BottomBarItem(
    val graph: String,
    val route: String,
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
) {
    TODAY(
        graph = Graph.TODAY,
        route = TodayScreen.Today.route,
        title = R.string.today,
        icon = R.drawable.baseline_today_24,
    ),

    COMPLETE(
        graph = Graph.COMPLETE,
        route = CompleteScreen.Complete.route,
        title = R.string.complete,
        icon = R.drawable.baseline_playlist_add_check_24,
    ),

    TASKS(
        graph = Graph.TASKS,
        route = TasksScreen.Tasks.route,
        title = R.string.tasks,
        icon = R.drawable.baseline_format_list_bulleted_24,
    ),

    SETTINGS(
        graph = Graph.SETTINGS,
        route = SettingsScreen.Settings.route,
        title = R.string.settings,
        icon = R.drawable.baseline_settings_24,
    ),
}