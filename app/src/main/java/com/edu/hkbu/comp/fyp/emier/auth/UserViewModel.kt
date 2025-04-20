package com.edu.hkbu.comp.fyp.emier.auth

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val token = mutableStateOf<String?>(null)

    fun loadToken(context: Context) {
        token.value = TokenStorage.getToken(context)
    }

    fun clearToken(context: Context) {
        TokenStorage.deleteToken(context)
        token.value = null
    }
}