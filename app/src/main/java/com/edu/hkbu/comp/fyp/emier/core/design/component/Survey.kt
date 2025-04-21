package com.edu.hkbu.comp.fyp.emier.core.design.component

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edu.hkbu.comp.fyp.emier.viewModel.SurveyViewModel

@Composable
fun Survey(context: Context) {
    val surveyViewModel: SurveyViewModel = viewModel()

    if (surveyViewModel.isSurveyAlreadyCompleted(context)) {
        Text(
            text = "你已經完成這份問卷。",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        if (surveyViewModel.isSurveyComplete.value) {
            Text(
                text = "問卷完成！你對自己的想法有瞭解更多了嗎？",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
            LaunchedEffect(Unit) {
                surveyViewModel.saveSurveyCompletion(context)
            }
        } else {
            val currentQuestion = surveyViewModel.questionList[surveyViewModel.currentQuestionIndex.value]
            MultipleChoiceSurvey(
                title = currentQuestion.title,
                question = currentQuestion.question,
                onOptionSelected = {
                    surveyViewModel.moveToNextQuestion()
                }
            )
        }
    }
}