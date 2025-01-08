package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.auth.AuthenticationManager

class UserViewModel(
    val authenticationManager: AuthenticationManager
) : ViewModel() {
    var isLoggedIn = mutableStateOf(false)


    suspend fun signOut(){
        authenticationManager.signOut()
        isLoggedIn.value = false
    }
}