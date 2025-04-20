package com.edu.hkbu.comp.fyp.emier.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val PREFS_NAME = "app_preferences"
    private const val KEY_HAS_SEEN_GUIDE = "has_seen_guide"

    fun hasSeenGuide(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_HAS_SEEN_GUIDE, false)
    }

    fun setHasSeenGuide(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_HAS_SEEN_GUIDE, true).apply()
    }
}