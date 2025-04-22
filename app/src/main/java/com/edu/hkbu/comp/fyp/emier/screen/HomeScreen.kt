package com.edu.hkbu.comp.fyp.emier.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.api.RetrofitInstance
import com.edu.hkbu.comp.fyp.emier.auth.UserViewModel
import com.edu.hkbu.comp.fyp.emier.navigation.Routes
import com.edu.hkbu.comp.fyp.emier.core.design.component.KnowMoreAboutYourFeelings
import com.edu.hkbu.comp.fyp.emier.utils.PreferencesUtil
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(fontSize: TextUnit, navController: NavHostController, userViewModel: UserViewModel){
    var hasLaunchedEffect by rememberSaveable { mutableStateOf(false) }
    Column() {
        Greeting(fontSize, userViewModel, hasLaunchedEffect) {
            hasLaunchedEffect = true
        }
        RelaxCard(fontSize, navController = navController)
        KnowMoreAboutYourFeelings(fontSize, navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(fontSize: TextUnit, userViewModel: UserViewModel, hasLaunchedEffect: Boolean, onEffectLaunched: () -> Unit) {
    val token = userViewModel.token.value
    val deviceToken = userViewModel.deviceToken.value

    LaunchedEffect(token, hasLaunchedEffect) {
        if (!token.isNullOrBlank() && !hasLaunchedEffect) {
            try {
                Log.d("HomeScreen", "Fetching stress data with token: $token")
                val endTime = Instant.now().epochSecond.toString()
                val startTime = (Instant.now().epochSecond - 24 * 60 * 60).toString()

                val response = RetrofitInstance.apiService.getStressData(
                    uat = token,
                    startTime = startTime,
                    endTime = endTime
                )

                if (response.isSuccessful) {
                    Log.d("HomeScreen", "Stress data fetched successfully: ${response.body()}")
                } else {
                    Log.e(
                        "HomeScreen",
                        "Failed to fetch stress data: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error fetching stress data", e)
            } finally {
                onEffectLaunched()
            }
        }
        if (!deviceToken.isNullOrBlank() && !hasLaunchedEffect && token != null) {
            userViewModel.registerDeviceToken(token, deviceToken)
            onEffectLaunched()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (token.isNullOrBlank()) "你好，請連接Garmin以解鎖更多功能" else "祝你有愉快的每一天",
                fontSize = fontSize,
                modifier = Modifier.width(200.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.greeting),
                contentScale = ContentScale.Fit,
                contentDescription = "Relax",
                modifier = Modifier.size(248.dp)
            )
        }
    }
}

@Composable
fun RelaxCard(fontSize: TextUnit, modifier: Modifier = Modifier, navController: NavHostController) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            .clickable { navController.navigate(Routes.Relax) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.yoga),
                contentDescription = "Relax",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(86.dp)
                    .padding(8.dp),
            )
            Text(
                text = stringResource(id = R.string.relax),
                fontSize = fontSize,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(18.dp)
            )
            Icon(
                Icons.Filled.ArrowForward, contentDescription = "Arrow Forward",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}