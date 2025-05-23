package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.edu.hkbu.comp.fyp.emier.api.RetrofitInstance
import com.edu.hkbu.comp.fyp.emier.auth.TokenStorage
import kotlinx.coroutines.launch

@Composable
fun DisconnectToGarminButton(onDisconnected: () -> Unit) {
    val context = LocalContext.current
    val userAccessToken = TokenStorage.getToken(context)
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Button(
        onClick = { showDialog = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("斷開Garmin連結")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    userAccessToken?.let { token ->
                        coroutineScope.launch {
                            try {
                                val response = RetrofitInstance.apiService.deregisterUser(token)
                                if (response.isSuccessful) {
                                    TokenStorage.deleteToken(context)
                                    snackbarHostState.showSnackbar("斷開成功")
                                    onDisconnected()
                                } else {
                                    snackbarHostState.showSnackbar("斷開失敗: ${response.message()}")
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("發生錯誤: $e")
                            }
                        }
                    }
                    showDialog = false
                }) {
                    Text("確定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("取消")
                }
            },
            title = { Text("斷開Garmin連結") },
            text = { Text("確定斷開與Garmin的連結嗎？之後可以使用同樣賬戶登入。") }
        )
    }

    SnackbarHost(hostState = snackbarHostState)
}