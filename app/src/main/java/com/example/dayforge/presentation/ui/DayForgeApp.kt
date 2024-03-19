package com.example.dayforge.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dayforge.R
import com.example.dayforge.presentation.models.BottomBarItem
import com.example.dayforge.presentation.navigation.RootNavGraph
import com.example.dayforge.presentation.navigation.TodayScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayForgeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val bottomItems = BottomBarItem.entries.toTypedArray()
    val bottomBarRoutes = bottomItems.map { it.route }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showFab = currentDestination?.route in bottomBarRoutes.minus(BottomBarItem.SETTINGS.route)
    val showBottomBar = currentDestination?.route in bottomBarRoutes

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar {
                    bottomItems.forEach { bottomItem ->
                        AddItem(
                            currentDestination = currentDestination,
                            navController = navController,
                            item = bottomItem,
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        navController.navigate("add_new_task")
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = stringResource(R.string.add_new_task),
                    )
                }
            }
        },
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            RootNavGraph(navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    currentDestination: NavDestination?,
    navController: NavController,
    item: BottomBarItem,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        modifier = modifier,
        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
        onClick = {
            navController.navigate(item.graph) {
                popUpTo(TodayScreen.Today.route)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(R.string.bottom_navigation_item),
            )
        },
        label = { Text(text = stringResource(item.title)) },
    )
}