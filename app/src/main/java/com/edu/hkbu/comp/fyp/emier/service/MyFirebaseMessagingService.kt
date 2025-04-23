package com.edu.hkbu.comp.fyp.emier.service

import android.util.Log
import com.edu.hkbu.comp.fyp.emier.api.RetrofitInstance
import com.edu.hkbu.comp.fyp.emier.auth.TokenStorage
import com.edu.hkbu.comp.fyp.emier.utils.NotificationUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMessagingService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        TokenStorage.saveDeviceToken(applicationContext, token)

        val uat = TokenStorage.getToken(applicationContext)
        if (uat != null) {
            sendRegistrationToServer(uat, token)
        } else {
            Log.e(TAG, "User access token (uat) is null")
        }
    }

    private fun sendRegistrationToServer(uat: String, token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.registerToken(uat, token)
                if (response.isSuccessful) {
                    Log.d(TAG, "Token successfully sent to server")
                } else {
                    Log.e(TAG, "Failed to send token to server: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error sending token to server", e)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Log the sender
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if the message contains a data payload
        remoteMessage.data?.let { data ->
            val latestStressLevel = data["latestStressLevel"] ?: "0"

            if (latestStressLevel.isNotEmpty()) {
                showNotification(latestStressLevel)
            } else {
                Log.d(TAG, "Latest stress level is empty")
            }
        }
    }

    private fun showNotification(body: String) {
        var title: String
        var message: String
        try {
            val stressLevel = body.toInt()
            when {
                stressLevel == 0 || stressLevel == -1 -> {
                    title = "請佩戴Gamin手帶"
                    message = "請佩戴Gamin手帶以監察你的壓力水平."
                }
                stressLevel > 50 -> {
                    title = "是時候要放鬆一下了"
                    message = "來看看你記錄下的放鬆活動吧"
                }
                else -> {
                    title = ""
                    message = ""
                }
            }
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Invalid stress level received: $body", e)
            title = "收到無效的壓力數據。"
            message = "請檢查你的Gamin手帶連接狀態。"
        }
        if (message.isNotEmpty()) {
            NotificationUtil.showNotification(applicationContext, title, message)
        }
    }

}