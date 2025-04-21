package com.edu.hkbu.comp.fyp.emier.utils

import android.content.Context

object ProgressUtil {
    private const val PREFS_NAME = "ReadingProgressPrefs"

    fun saveProgress(context: Context, emotion: String, progress: Float) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putFloat(emotion, progress).apply()
    }

    fun getProgress(context: Context, emotion: String): Float {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val progress = sharedPreferences.getFloat(emotion, 0f)
        return progress
    }
}