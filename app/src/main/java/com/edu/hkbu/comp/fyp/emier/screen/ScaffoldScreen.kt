package com.edu.hkbu.comp.fyp.emier.screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.auth.UserViewModel
import com.edu.hkbu.comp.fyp.emier.core.design.component.VideoDetailScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.DepressionScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.AnxietyScreen
import com.edu.hkbu.comp.fyp.emier.navigation.NavDestination
import com.edu.hkbu.comp.fyp.emier.navigation.Routes
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.AngerScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.DisappointmentScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.GuiltScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.LonelyScreen
import com.edu.hkbu.comp.fyp.emier.screen.feelingScreen.NervousScreen
import com.edu.hkbu.comp.fyp.emier.utils.PreferencesUtil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScaffoldScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    playerViewModel: VideoPlayerViewModel,
    startDestination: String
) {
    val currentDestination by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    userViewModel.loadFontSize(context)
    val fontSize = userViewModel.fontSize.value

    Scaffold(
        topBar = {
            MyTopBar(currentDestination, navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { innerPadding ->
            Content(fontSize, innerPadding, navController, playerViewModel, userViewModel, startDestination)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(currentDestination: NavBackStackEntry?, navController: NavHostController) {

    if (currentDestination?.destination?.route == Routes.Guide) {
        return
    }
    
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.blue)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    fontSize: TextUnit,
    innerPadding: PaddingValues,
    navController: NavHostController,
    playerViewModel: VideoPlayerViewModel,
    userViewModel: UserViewModel,
    startDestination: String
) {
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        val videos = listOf(
            Video(1, "認識你的情緒", R.raw.introduction, R.string.splash_page1),
            Video(2, "認知行爲治療", R.raw.cbt, R.string.splash_page4),
            Video(3, "認識抑鬱", R.raw.depression_part1, R.string.depression_intro),
            Video(4, "抑鬱的思想陷阱", R.raw.depression_part2, R.string.depression_intro2),
            Video(5, "抑鬱故事", R.raw.depression_story, R.string.depression_story),
        )

        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(Routes.Guide) { GuideScreen(navController, playerViewModel) }
            composable(Routes.Home) { HomeScreen(fontSize, navController, userViewModel) }
            composable(Routes.Tutorial) { TutorialScreen(navController, videos) }
            composable(Routes.Settings) { SettingsScreen(userViewModel) }
            composable(Routes.Relax) { RelaxScreen() }
            composable(Routes.Anger) { AngerScreen() }
            composable(Routes.Anxiety) { AnxietyScreen() }
            composable(Routes.Depression) { DepressionScreen() }
            composable(Routes.Disappointment) { DisappointmentScreen() }
            composable(Routes.Guilt) { GuiltScreen() }
            composable(Routes.Lonely) { LonelyScreen() }
            composable(Routes.Nervous) { NervousScreen() }
            composable("video/{videoId}") { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId")?.toIntOrNull()
                val video = videos.find { it.id == videoId }
                if (video != null) {
                    VideoDetailScreen(fontSize = fontSize, video = video, playerViewModel = playerViewModel)
                }
            }
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
        containerColor = colorResource(id = R.color.blue)
    ) {
        navigationBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
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