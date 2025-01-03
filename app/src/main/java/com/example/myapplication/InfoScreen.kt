package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class UserPreference(val fontSize: Float)

@Composable
fun InfoScreen() {
    var expanded by remember { mutableStateOf(false) }
    var selectedFontSize by remember { mutableStateOf(16.sp) }
    val fontSizes = listOf(12.sp, 16.sp, 20.sp)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User Preferences", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { expanded = true }) {
                Text(text = "Choose Font Size: ${selectedFontSize.value}sp")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                fontSizes.forEach { fontSize ->
                    DropdownMenuItem(
                        text = { Text(text = "${fontSize.value}sp") },
                        onClick = {
                            selectedFontSize = fontSize
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Sample Text", fontSize = selectedFontSize)
    }
}