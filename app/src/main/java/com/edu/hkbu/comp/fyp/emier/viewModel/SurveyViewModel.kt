package com.edu.hkbu.comp.fyp.emier.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class SurveyQuestion(
    val title: String,
    val question: String,
    val options: List<String>
)

class SurveyViewModel : ViewModel() {
    var questionList = listOf(
        SurveyQuestion("非黑即白", "即「絕對化」思想；事情只有一個絕對的結果，不可能存在其他可能性，換句話説，這些人對事情的看法只有是或不是，錯與對，中間沒有灰色地帶。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("攬曬上身", "即「個人化」思想；每當出現問題時，有這種思維的人往往把責任歸咎與自己身上，並認爲是自己的錯。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("貶低成功經驗", "這些人把成功的經驗歸因於別人的身上，會認爲這只是僥倖，或沒有什麽了不起，並沒有體驗為自己的努力所至。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("大難臨頭", "把事情的嚴重程度擴大，推至「災難化」的地步。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("打沉自己", "這些人不斷向自己説負面的説話，以致意志消沉。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("妄下判斷", "在沒有什麽理據下，把事情的結果推斷為負面。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("左思右想", "面對事情不斷重覆思考，而思考内容是互相矛盾，不能果斷，猶豫不決。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("感情用事", "以心情做判斷或結論，心情壞的時候什麽都有問題而忽略事情的客觀事實。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("怨天尤人", "忽略貨推卸自己責任，凡是歸咎他人或埋怨上天。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
        SurveyQuestion("猜度人意", "揣測別人的行爲及神態背後的心思意念。", listOf("完全沒有", "很少", "不知道", "簡中", "時常")),
    )
    var currentQuestionIndex = mutableStateOf(0)
    var isSurveyComplete = mutableStateOf(false)

    fun moveToNextQuestion() {
        if (currentQuestionIndex.value < questionList.size - 1) {
            currentQuestionIndex.value++
        } else {
            isSurveyComplete.value = true
        }
    }

    fun saveSurveyCompletion(context: Context) {
        val sharedPreferences = context.getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("Survey1Completed", true).apply()
    }

    fun isSurveyAlreadyCompleted(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Survey1Completed", false)
    }
}