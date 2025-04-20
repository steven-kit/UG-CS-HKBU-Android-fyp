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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.edu.hkbu.comp.fyp.emier.auth.TokenStorage
import com.edu.hkbu.comp.fyp.emier.screen.ScaffoldScreen
import com.edu.hkbu.comp.fyp.emier.core.design.theme.AppTheme

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
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScaffoldScreen()
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        ScaffoldScreen()
    }
}