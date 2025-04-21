package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultipleChoiceSurvey(
    title: String,
    question: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("完全沒有", "很少", "不知道", "簡中", "時常")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = question,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        onOptionSelected(option)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = option,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}