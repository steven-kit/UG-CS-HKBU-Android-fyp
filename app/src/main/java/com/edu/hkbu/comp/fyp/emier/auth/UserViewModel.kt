package com.edu.hkbu.comp.fyp.emier.auth

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.edu.hkbu.comp.fyp.emier.auth.AuthenticationManager

class UserViewModel(
    val authenticationManager: AuthenticationManager,
) : ViewModel() {
    var isLoggedIn = mutableStateOf(false)

    suspend fun signOut(){
        authenticationManager.signOut()
        isLoggedIn.value = false
    }
}