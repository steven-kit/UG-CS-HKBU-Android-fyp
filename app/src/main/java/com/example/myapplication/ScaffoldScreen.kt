package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.feelingScreens.DepressionScreen
import com.example.myapplication.feelingScreens.AnxietyScreen
import com.example.myapplication.navigation.NavDestination
import com.example.myapplication.navigation.Routes
import com.example.myapplication.components.KnowMoreAboutYourFeelings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen() {
    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()
    val items = listOf(
        NavDestination.Home,
        NavDestination.Tutorial,
        NavDestination.Info,
        NavDestination.Relax,
        NavDestination.Depression,
        NavDestination.Anger,
        NavDestination.Anxiety,
        NavDestination.Disappointment,
        NavDestination.Fear
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { currentDestination?.destination?.route?.let { route ->
                    val destination = items.find { it.route == route }
                    destination?.title?.let { Text(it) }
                } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.outlineVariant
                ),
                navigationIcon = {
                    when (currentDestination?.destination?.route) {
                        Routes.Home, Routes.Tutorial, Routes.Settings -> { }
                        else -> {
                            IconButton(onClick = navController::popBackStack) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    }
                },
                actions = {
                    when (currentDestination?.destination?.route) {
                        Routes.Home, Routes.Tutorial, Routes.Settings -> { }
                        else -> {
                            IconButton(onClick = {
                                navController.navigate(Routes.Home) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { innerPadding ->
            Content(innerPadding, navController)
        }
    )
}

@Composable
fun Content(innerPadding: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Home,
        ) {
            composable(Routes.Home) { HomeScreen(navController) }
            composable(Routes.Tutorial) { TutorialScreen(navController) }
            composable(Routes.Settings) { InfoScreen() }
            composable(Routes.Depression) { DepressionScreen() }
            composable(Routes.Relax) { RelaxScreen() }
            composable(Routes.Anxiety) { AnxietyScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavDestination.Home,
        NavDestination.Tutorial,
        NavDestination.Info
    )
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    if (currentDestination !in items.map { it.route }) {
        return
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.outlineVariant
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(items[selectedItem].route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.outlineVariant,
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldPreview() {
    ScaffoldScreen()
}