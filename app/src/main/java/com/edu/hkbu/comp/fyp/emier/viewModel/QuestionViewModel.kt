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
    var currentQuestionIndex by mutableStateOf(0)
    var activeQuestionList: List<Question> = listOf()

    var anxietyQuestionList by mutableStateOf(
        listOf(
            Question("身體反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "面紅耳赤，手心冒汗，心跳加快"),
            Question("行爲反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "雙眼泛紅，口窒窒"),
            Question("情緒反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "羞愧、沮喪、無助"),
            Question("思維反應", "每天下班回家路上致電琪琪媽媽問當天的功課", "又要厚著面皮去麻煩琪琪媽媽，不知會否有一天被嫌棄或討厭，又或覺得自己沒有好好管教菲菲縱容她不抄家課冊")
        )
    )

    var depressionQuestionList by mutableStateOf(
        listOf(
            Question("身體反應", "醫務所取報告", "身體變得彊硬、呆滯、說不出話來"),
            Question("行爲反應", "醫務所取報告", "從醫務所跑了出去"),
            Question("情緒反應", "醫務所取報告", "沮喪"),
            Question("思維反應", "醫務所取報告", "為什麼是自己女兒，是否自己年紀大才有孕以至女兒健康出問題")
        )
    )

    fun setDepressionQuestionList() {
        activeQuestionList = depressionQuestionList
    }

    fun setAnxietyQuestionList() {
        activeQuestionList = anxietyQuestionList
    }

    fun moveToNextQuestion() {
        val listSize = activeQuestionList.size
        if (currentQuestionIndex < listSize - 1) {
            currentQuestionIndex++
        }
        else {
            currentQuestionIndex = listSize
        }
    }

    fun resetQuestionIndex() {
        currentQuestionIndex = 0
    }

}