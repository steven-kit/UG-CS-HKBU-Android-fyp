package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.R

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector, val imageResId: Int? = null) {
    object Home : NavDestination(title = "Home", route = Routes.Home, icon = Icons.Filled.Home)

    object Tutorial : NavDestination(title = "Tutorial", route = Routes.Tutorial, icon = Icons.Filled.Create)

    object Info : NavDestination(title = "Settings", route = Routes.Settings, icon = Icons.Filled.Settings)

    object Relax : NavDestination(title = "Relax Yourself", route = Routes.Relax, icon = Icons.Filled.Build)

    object Depression : NavDestination(title = "抑鬱", route = Routes.Depression, icon = Icons.Filled.Build, imageResId = R.drawable.depression)

    object Anger : NavDestination(title = "憤怒", route = Routes.Depression, icon = Icons.Filled.Build, imageResId = R.drawable.anger)

    object Anxiety : NavDestination(title = "焦慮", route = Routes.Anxiety, icon = Icons.Filled.Build, imageResId = R.drawable.anxiety)

    object Disappointment : NavDestination(title = "失望", route = Routes.Anxiety, icon = Icons.Filled.Build, imageResId = R.drawable.ennui)

    object Fear : NavDestination(title = "恐懼", route = Routes.Anxiety, icon = Icons.Filled.Build, imageResId = R.drawable.fear)
}