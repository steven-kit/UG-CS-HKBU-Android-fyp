package com.edu.hkbu.comp.fyp.emier.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object TokenStorage {

    private const val PREFS_NAME = "secure_prefs"
    private const val TOKEN_KEY = "user_access_token"

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
}