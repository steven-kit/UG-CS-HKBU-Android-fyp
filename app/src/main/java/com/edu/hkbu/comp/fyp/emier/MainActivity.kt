package com.edu.hkbu.comp.fyp.emier

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.edu.hkbu.comp.fyp.emier.auth.TokenStorage
import com.edu.hkbu.comp.fyp.emier.auth.UserViewModel
import com.edu.hkbu.comp.fyp.emier.screen.ScaffoldScreen
import com.edu.hkbu.comp.fyp.emier.core.design.theme.AppTheme
import com.edu.hkbu.comp.fyp.emier.navigation.Routes
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel
import com.edu.hkbu.comp.fyp.emier.utils.PreferencesUtil

class MainActivity : ComponentActivity() {
    companion object {
        var connectionResult = mutableStateOf<String?>(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        handleDeepLink(intent)

        setContent {
            val navController = rememberNavController()

            val playerViewModel: VideoPlayerViewModel = viewModel()

            val userViewModel: UserViewModel = viewModel()
            userViewModel.loadToken(this)

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScaffoldScreen(
                        navController = navController,
                        userViewModel = userViewModel,
                        playerViewModel = playerViewModel,
                        startDestination = if (PreferencesUtil.hasSeenGuide(this)) Routes.Home else Routes.Guide
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val data: Uri? = intent?.data
        if (data != null && data.scheme == "myapp" && data.host == "oauth-success") {
            val accessToken = data.getQueryParameter("uat")
            if (accessToken != null) {
                TokenStorage.saveToken(this, accessToken)
                Log.d("MainActivity", "Access Token: $accessToken")
                connectionResult.value = "連結成功！"
            } else {
                connectionResult.value = "連結失敗，請重試。"
            }
        }
    }
}