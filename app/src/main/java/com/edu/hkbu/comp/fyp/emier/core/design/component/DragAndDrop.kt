package com.edu.hkbu.comp.fyp.emier.core.design.component

import android.content.ClipData
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.utils.annotatedStringResource
import com.edu.hkbu.comp.fyp.emier.viewModel.QuestionViewModel

@Composable
fun DragAndDrop(fontSize: TextUnit, questionViewModel: QuestionViewModel, draggableText: Int){
    DraggableText(fontSize, annotatedStringResource(draggableText))
    when (draggableText) {
        R.string.anxiety_story -> questionViewModel.setAnxietyQuestionList()
        R.string.depression_story -> questionViewModel.setDepressionQuestionList()
    }
    if (questionViewModel.currentQuestionIndex < questionViewModel.activeQuestionList.size) {
        val currentQuestion = questionViewModel.activeQuestionList[questionViewModel.currentQuestionIndex]

        key(questionViewModel.currentQuestionIndex) {
            QuestionCard(
                fontSize = fontSize,
                title = currentQuestion.title,
                question = currentQuestion.question,
                answer = currentQuestion.answer,
                onAnswerCorrect = {
                    questionViewModel.moveToNextQuestion()
                }
            )
        }
    } else {
        Text(
            text = "全部答對了！！若果你是主角，也會有一樣的想法嗎？",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DraggableText(fontSize: TextUnit, annotatedString: AnnotatedString) {
    val draggableWords = annotatedString.getStringAnnotations("draggable", 0, annotatedString.length)
    Column {
        LazyRow {
            items(draggableWords) { word ->
                DraggableWord(fontSize, word.item)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableWord(fontSize: TextUnit, word: String) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.LightGray)
            .dragAndDropSource {
                detectTapGestures(onLongPress = {
                    startTransfer(
                        DragAndDropTransferData(
                            ClipData.newPlainText("text", word),
                            flags = View.DRAG_FLAG_GLOBAL
                        )
                    )
                }
                )
            }
    ) {
        Text(text = word, fontSize = fontSize, modifier = Modifier.padding(8.dp))
    }
}
