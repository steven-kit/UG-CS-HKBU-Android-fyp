package com.edu.hkbu.comp.fyp.emier.auth

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.hkbu.comp.fyp.emier.api.RetrofitInstance
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val token = mutableStateOf<String?>(null)
    val deviceToken = mutableStateOf<String?>(null)

    fun loadToken(context: Context) {
        token.value = TokenStorage.getToken(context)
    }

    fun loadDeviceToken(context: Context) {
        deviceToken.value = TokenStorage.getDeviceToken(context)
    }

    fun clearToken(context: Context) {
        TokenStorage.deleteToken(context)
        token.value = null
    }

    fun clearDeviceToken(context: Context) {
        TokenStorage.deleteDeviceToken(context)
        deviceToken.value = null
    }

    fun registerDeviceToken(uat: String, deviceToken: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.registerToken(
                    uat = uat,
                    deviceId = deviceToken
                )
                if (response.isSuccessful) {
                    Log.d("UserViewModel", "Device token registered successfully")
                } else {
                    Log.e("UserViewModel", "Failed to register device token: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error registering device token", e)
            }
        }
    }
}