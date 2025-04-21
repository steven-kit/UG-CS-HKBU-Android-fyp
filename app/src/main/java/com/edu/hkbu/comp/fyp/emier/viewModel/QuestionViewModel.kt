package com.edu.hkbu.comp.fyp.emier.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class Question(
    val title: String,
    val question: String,
    val answer: String
)

class QuestionViewModel : ViewModel() {
    var questionList by mutableStateOf(
        listOf(
            Question("身體反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "面紅耳赤，手心冒汗，心跳加快"),
            Question("行爲反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "雙眼泛紅，口窒窒"),
            Question("情緒反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "羞愧、沮喪、無助"),
            Question("思維反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "又要厚著面皮去麻煩琪琪媽媽，不知會否有一天被嫌棄或討厭，又或覺得自己沒有好好管教菲菲縱容她不抄家課冊")
        )
    )
    var currentQuestionIndex by mutableStateOf(0)

    fun moveToNextQuestion() {
        if (currentQuestionIndex < questionList.size - 1) {
            currentQuestionIndex++
        } else {
            currentQuestionIndex = questionList.size
        }
    }

}