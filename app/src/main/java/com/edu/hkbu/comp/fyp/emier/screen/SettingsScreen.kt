package com.edu.hkbu.comp.fyp.emier.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.edu.hkbu.comp.fyp.emier.MainActivity
import com.edu.hkbu.comp.fyp.emier.auth.TokenStorage
import com.edu.hkbu.comp.fyp.emier.auth.UserViewModel
import com.edu.hkbu.comp.fyp.emier.core.design.component.ConnectToGarminButton
import com.edu.hkbu.comp.fyp.emier.core.design.component.DisconnectToGarminButton
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class UserPreference(val fontSize: Float)

@Composable
fun SettingsScreen(userViewModel: UserViewModel) {
    val connectionResult = MainActivity.connectionResult

    var expanded by remember { mutableStateOf(false) }
    var selectedFontSize by remember { mutableStateOf(16.sp) }
    val fontSizes = listOf(12.sp, 16.sp, 20.sp)

    val context = LocalContext.current
    val token = userViewModel.token.value

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "連接Garmin Connect", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (token.isNullOrBlank()) {
            ConnectToGarminButton(onConnected = {
                userViewModel.loadToken(context)
            })
            Log.d("SettingsScreen", "User Access Token: ${TokenStorage.getToken(LocalContext.current)}")
        } else {
            DisconnectToGarminButton(onDisconnected = {
                userViewModel.clearToken(context)
            })
            Log.d("SettingsScreen", "User Access Token: ${TokenStorage.getToken(LocalContext.current)}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "使用者偏好", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { expanded = true }) {
                Text(text = "選擇字體大小: ${selectedFontSize.value}sp")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                fontSizes.forEach { fontSize ->
                    DropdownMenuItem(
                        text = { Text(text = "${fontSize.value}sp") },
                        onClick = {
                            selectedFontSize = fontSize
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "範例文字", fontSize = selectedFontSize)
    }

    // Connect to Garmin Alert Dialog
    connectionResult.value?.let { message ->
        AlertDialog(
            onDismissRequest = { MainActivity.connectionResult.value = null },
            confirmButton = {
                TextButton(onClick = { MainActivity.connectionResult.value = null }) {
                    Text("OK")
                }
            },
            title = { Text("連結結果") },
            text = { Text(message) }
        )
    }
}