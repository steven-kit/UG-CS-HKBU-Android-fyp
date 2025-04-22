package com.edu.hkbu.comp.fyp.emier.auth

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.messaging.FirebaseMessaging

object TokenStorage {

    private const val PREFS_NAME = "secure_prefs"
    private const val TOKEN_KEY = "user_access_token"
    private const val DEVICE_TOKEN_KEY = "device_token"

    private fun getSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveToken(context: Context, token: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(TOKEN_KEY, null)
    }

    fun deleteToken(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(TOKEN_KEY).apply()
    }

    fun saveDeviceToken(context: Context, deviceToken: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(DEVICE_TOKEN_KEY, deviceToken).apply()

    }

    fun getDeviceToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(DEVICE_TOKEN_KEY, null)
    }

    fun deleteDeviceToken(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(DEVICE_TOKEN_KEY).apply()
    }

    fun fetchAndSaveDeviceToken(context: Context) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newDeviceToken = task.result
                    if (!newDeviceToken.isNullOrEmpty()) {
                        saveDeviceToken(context, newDeviceToken)
                        Log.d("UserViewModel", "Device token fetched and saved: $newDeviceToken")
                    } else {
                        Log.e("UserViewModel", "Failed to fetch device token: Token is null or empty")
                    }
                } else {
                    Log.e("UserViewModel", "Failed to fetch device token", task.exception)
                }
            }
    }
}