package com.edu.hkbu.comp.fyp.emier.screen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edu.hkbu.comp.fyp.emier.auth.UserViewModel
import com.edu.hkbu.comp.fyp.emier.auth.AuthenticationManager
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.DepressionScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.AnxietyScreen
import com.edu.hkbu.comp.fyp.emier.navigation.NavDestination
import com.edu.hkbu.comp.fyp.emier.navigation.Routes
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.AngerScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.GuiltScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.LonelyScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.NervousScreen

@Composable
fun ScaffoldScreen() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val authenticationManager = remember {
        AuthenticationManager(context)
    }
    val userViewModel = remember {
        UserViewModel(authenticationManager)
    }

    val currentDestination by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            MyTopBar(currentDestination, navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { innerPadding ->
            Content(innerPadding, navController, userViewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(currentDestination: NavBackStackEntry?, navController: NavHostController) {

    if (currentDestination?.destination?.route == Routes.Splash) {
        return
    }
    
    TopAppBar(
        title = { 
//            currentDestination?.destination?.route?.let { route ->
//            val destination = items.find { it.route == route }
//            destination?.title?.let { Text(it) }
//        } 
        },
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
                        navController.navigate(Routes.Home)
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
}

@Composable
fun Content(innerPadding: PaddingValues, navController: NavHostController, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Guide,
        ) {
            composable(Routes.Guide) { GuideScreen(navController) }
            composable(Routes.Login) { LoginScreen(navController, userViewModel) }
            composable(Routes.Home) { HomeScreen(navController, userViewModel) }
            composable(Routes.Tutorial) { TutorialScreen(navController) }
            composable(Routes.Settings) { SettingsScreen(navController, userViewModel) }
            composable(Routes.Relax) { RelaxScreen() }
            composable(Routes.Anger) { AngerScreen() }
            composable(Routes.Anxiety) { AnxietyScreen() }
            composable(Routes.Depression) { DepressionScreen() }
            composable(Routes.Disappointment) { DepressionScreen() }
            composable(Routes.Guilt) { GuiltScreen() }
            composable(Routes.Lonely) { LonelyScreen() }
            composable(Routes.Nervous) { NervousScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState()
    var selectedItem by remember { mutableIntStateOf(0) }
    val navigationBarItems = listOf(
        NavDestination.Home,
        NavDestination.Tutorial,
        NavDestination.Settings
    )
    val currentRoute = currentDestination.value?.destination?.route

    if (currentRoute !in navigationBarItems.map { it.route }) {
        return
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.outlineVariant
    ) {
        navigationBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    selectedItem = index
                    navController.navigate(navigationBarItems[selectedItem].route) {
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