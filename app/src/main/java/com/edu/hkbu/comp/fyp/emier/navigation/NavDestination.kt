package com.edu.hkbu.comp.fyp.emier.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.edu.hkbu.comp.fyp.emier.R

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector, val imageResId: Int? = null) {
    data object Guide : NavDestination(title = "Guide", route = Routes.Guide, icon = Icons.Filled.Build)
    data object Splash : NavDestination(title = "Splash", route = Routes.Splash, icon = Icons.Filled.Build)
    data object Login : NavDestination(title = "Login", route = Routes.Login, icon = Icons.Filled.AccountCircle)
    data object Home : NavDestination(title = "Home", route = Routes.Home, icon = Icons.Filled.Home)

    data object Tutorial : NavDestination(title = "Tutorial", route = Routes.Tutorial, icon = Icons.Filled.Create)

    data object Settings : NavDestination(title = "Settings", route = Routes.Settings, icon = Icons.Filled.Settings)

    data object Relax : NavDestination(title = "Relax Yourself", route = Routes.Relax, icon = Icons.Filled.Build)

    data object Anger : NavDestination(title = "憤怒", route = Routes.Anger, icon = Icons.Filled.Build, imageResId = R.drawable.anger)

    data object Anxiety : NavDestination(title = "焦慮", route = Routes.Anxiety, icon = Icons.Filled.Build, imageResId = R.drawable.anxiety)

    data object Depression : NavDestination(title = "抑鬱", route = Routes.Depression, icon = Icons.Filled.Build, imageResId = R.drawable.depression)
    data object Disappointment : NavDestination(title = "失望", route = Routes.Disappointment, icon = Icons.Filled.Build, imageResId = R.drawable.disappointment)

    data object Guilt : NavDestination(title = "内疚", route = Routes.Guilt, icon = Icons.Filled.Build, imageResId = R.drawable.guilt)

    data object Lonely : NavDestination(title = "孤獨", route = Routes.Lonely, icon = Icons.Filled.Build, imageResId = R.drawable.lonely)

    data object Nervous : NavDestination(title = "緊張", route = Routes.Nervous, icon = Icons.Filled.Build, imageResId = R.drawable.nervous)
}