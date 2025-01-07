package com.example.myapplication.components

import android.content.ClipDescription
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionCard(title: String, question: String, answer: String) {
    var userAnswer by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    val callback = remember {
        object : DragAndDropTarget {
            override fun onDrop(event: DragAndDropEvent): Boolean {
                val draggedItem = event.toAndroidDragEvent().clipData.getItemAt(0).text.toString()
                userAnswer = draggedItem
                isCorrect = userAnswer == answer
                return true
            }

            override fun onStarted(event: DragAndDropEvent) {}
            override fun onEntered(event: DragAndDropEvent) {}
            override fun onEnded(event: DragAndDropEvent) {}
            override fun onExited(event: DragAndDropEvent) {}
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Cyan, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(200.dp)
            .dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = callback
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = question, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            if (isCorrect != null) {
                if (isCorrect == true) {
                    Text(text = "✔", color = Color.Green, fontSize = 24.sp)
                } else {
                    Text(text = "✘", color = Color.Red, fontSize = 24.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionCardPreview() {
    QuestionCard(
        title = "Sample Question",
        question = "What is 2 + 2?",
        answer = "4"
    )
}