package com.edu.hkbu.comp.fyp.emier.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PreferencesUtil {
    private const val PREFS_NAME = "app_preferences"
    private const val KEY_HAS_SEEN_GUIDE = "has_seen_guide"
    private const val RELAX_ITEMS_KEY = "relax_items"

    fun hasSeenGuide(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_HAS_SEEN_GUIDE, false)
    }

    fun setHasSeenGuide(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_HAS_SEEN_GUIDE, true).apply()
    }

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

    fun saveSelectedRelaxItems(context: Context, items: List<String>) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putStringSet(RELAX_ITEMS_KEY, items.toSet()).apply()
    }

    fun getSelectedRelaxItems(context: Context): List<String> {
        val prefs = getSharedPreferences(context)
        return prefs.getStringSet(RELAX_ITEMS_KEY, emptySet())?.toList() ?: emptyList()
    }
}