package com.edu.hkbu.comp.fyp.emier.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PreferencesUtil {
    private const val PREFS_NAME = "app_preferences"
    private const val KEY_HAS_SEEN_GUIDE = "has_seen_guide"
    private const val RELAX_ITEMS_KEY = "relax_items"
    private const val KEY_SELECTED_SURVEY_OPTIONS = "selected_survey_options"

    fun hasSeenGuide(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_HAS_SEEN_GUIDE, false)
    }

    fun setHasSeenGuide(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_HAS_SEEN_GUIDE, true).apply()
    }

    fun saveFontSize(context: Context, fontSize: Float) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putFloat("font_size", fontSize).apply()
    }

    fun getFontSize(context: Context): Float {
        val prefs = getSharedPreferences(context)
        return prefs.getFloat("font_size", 16f)
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

    fun saveSelectedSurveyOptions(context: Context, options: Set<String>) {
        val prefs = context.getSharedPreferences("survey_prefs", Context.MODE_PRIVATE)
        prefs.edit().putStringSet(KEY_SELECTED_SURVEY_OPTIONS, options).apply()
    }

    fun getSelectedSurveyOptions(context: Context): Set<String> {
        val prefs = context.getSharedPreferences("survey_prefs", Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_SELECTED_SURVEY_OPTIONS, emptySet()) ?: emptySet()
    }
}