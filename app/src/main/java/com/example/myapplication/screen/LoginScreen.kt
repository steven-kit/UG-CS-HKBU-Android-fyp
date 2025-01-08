package com.example.myapplication.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.UserViewModel
import com.example.myapplication.auth.AuthResponse
import com.google.android.gms.base.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(navController: NavHostController, userViewModel: UserViewModel) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val isRegister = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Sign in title
        Text(
            text = if (!isRegister.value) "登入" else "註冊",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email input field
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = !isValidEmail(it)
            },
            placeholder = { Text("Email") },
            leadingIcon = {
                Icon(Icons.Rounded.Email, contentDescription = null)
            },
            shape = RoundedCornerShape(16.dp),
            isError = emailError.value,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError.value) {
            Text("Invalid email address", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password input field
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = !isValidPassword(it)
            },
            placeholder = { Text("Password") },
            leadingIcon = {
                Icon(Icons.Rounded.Lock, contentDescription = null)
            },
            shape = RoundedCornerShape(16.dp),
            isError = passwordError.value,
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError.value) {
            Text("Password must be at least 6 characters", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign in button
        Button(
            onClick = {
                if (!isRegister.value) {
                    userViewModel.authenticationManager.signInWithEmail(email.value, password.value)
                        .onEach { response ->
                            if (response is AuthResponse.Success) {
                                userViewModel.isLoggedIn.value = true
                                navController.navigate("home")
                            }
                        }
                        .launchIn(coroutineScope)
                } else {
                    userViewModel.authenticationManager.createAccountWithEmail(email.value, password.value)
                        .onEach { response ->
                            if (response is AuthResponse.Success) {
                                navController.navigate("home")
                            }
                        }
                        .launchIn(coroutineScope)
                }
            },
            enabled = !emailError.value && !passwordError.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (!isRegister.value) "登入" else "註冊")
        }

        // Register button
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    isRegister.value = !isRegister.value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(text = if (!isRegister.value) "沒有帳戶？註冊" else "返回登入")
            }
        }

        Text("或以以下方式繼續")

        Spacer(modifier = Modifier.height(16.dp))

        // Google sign in button
        OutlinedButton(
            onClick = {
                userViewModel.authenticationManager.signInWithGoogle()
                    .onEach { response ->
                        if (response is AuthResponse.Success) {
                            userViewModel.isLoggedIn.value = true
                            navController.navigate("home")
                        }
                    }
                    .launchIn(coroutineScope)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.googleg_standard_color_18), contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Sign in with Google")
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}
