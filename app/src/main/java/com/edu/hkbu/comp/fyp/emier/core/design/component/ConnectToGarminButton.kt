package com.edu.hkbu.comp.fyp.emier.core.design.component

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun ConnectToGarminButton(onConnected: () -> Unit) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, "https://emier-backend-caacbzexavbfa7gn.eastus-01.azurewebsites.net/connect/garmin".toUri())
            context.startActivity(intent)
            onConnected()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("連接Garmin")
    }
}