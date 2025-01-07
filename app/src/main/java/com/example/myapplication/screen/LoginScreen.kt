package com.example.myapplication.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.auth.AuthResponse
import com.example.myapplication.auth.AuthenticationManager
import com.google.android.gms.base.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current

//    val googleAuthClient = GoogleSignInClient(context)

//    var isSignIn by rememberSaveable {
//        mutableStateOf(googleAuthClient.isSignedIn())
//    }

    val authenticationManager = remember {
        AuthenticationManager(context)
    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign in",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                authenticationManager.signInWithGoogle()
                    .onEach { response ->
                        if (response is AuthResponse.Success) {
                            Log.d("LoginScreen", "Sign in with Google success")
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

