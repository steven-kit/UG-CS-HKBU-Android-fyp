package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RelaxScreen() {
    var isEditing by remember { mutableStateOf(true) }
    var selectedItems by remember { mutableStateOf(emptyList<String>()) }
    val activities = listOf(
        "浸泡浴缸浴", "計劃週末假期活動", "放空____分鐘", "做自己喜愛的運動", "外出跑步/散步",
        "聽自己喜歡的音樂", "聽演唱會", "睇戲", "睇書/雜誌", "上網", "打機", "唱歌", "與朋友通電話",
        "與親友通電話", "跳舞", "做瑜伽", "冥想", "祈禱", "去沙灘散步/聽海浪聲", "煮食", "做甜品",
        "吃甜品/自己喜歡的食物", "游水", "煲劇", "寫日記/ 寫作", "到郊外呼叫吸新鮮空氣", "淋花/ 栽種植物",
        "餵伺寵物", "沖泡咖啡 / 茶 / 朱古力", "買衣服/鞋", "織毛衣", "做小手工/畫畫", "按摩"
    )

    Column(modifier = Modifier) {
        if (isEditing) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(activities) { activity ->
                    var isChecked by remember { mutableStateOf(selectedItems.contains(activity)) }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = activity)
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = it
                                selectedItems = if (it) {
                                    selectedItems + activity
                                } else {
                                    selectedItems - activity
                                }
                            }
                        )
                    }
                }
            }
            Button(
                onClick = { isEditing = false },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            ) {
                Text("Submit")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(selectedItems) { activity ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = activity)
                        Checkbox(
                            checked = true,
                            onCheckedChange = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RelaxScreenPreview() {
    RelaxScreen()
}