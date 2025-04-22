package com.edu.hkbu.comp.fyp.emier.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.edu.hkbu.comp.fyp.emier.utils.PreferencesUtil

@Composable
fun RelaxScreen() {
    val context = LocalContext.current
    var selectedItems by remember { mutableStateOf(PreferencesUtil.getSelectedRelaxItems(context)) }
    var isEditing by remember { mutableStateOf(false) }
    val allActivities = listOf(
        "浸泡浴缸浴", "計劃週末假期活動", "放空15分鐘", "做自己喜愛的運動", "外出跑步/散步",
        "聽自己喜歡的音樂", "聽演唱會", "睇戲", "睇書/雜誌", "上網", "打機", "唱歌", "與朋友通電話",
        "與親友通電話", "跳舞", "做瑜伽", "冥想", "祈禱", "去沙灘散步/聽海浪聲", "煮食", "做甜品",
        "吃甜品/自己喜歡的食物", "游水", "煲劇", "寫日記/ 寫作", "到郊外呼叫吸新鮮空氣", "淋花/ 栽種植物",
        "餵伺寵物", "沖泡咖啡 / 茶 / 朱古力", "買衣服/鞋", "織毛衣", "做小手工/畫畫", "按摩"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditing) {
                Text(
                    text = "選擇你喜愛的放鬆活動",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { isEditing = !isEditing }) {
                Text(if (isEditing) "完成" else "編輯")
            }
        }

        // Display Activities
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            val activitiesToShow = if (isEditing) allActivities else selectedItems
            items(activitiesToShow) { activity ->
                if (isEditing) {
                    // Checkbox for editing mode
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = activity)
                        Checkbox(
                            checked = selectedItems.contains(activity),
                            onCheckedChange = { isChecked ->
                                selectedItems = if (isChecked) {
                                    selectedItems + activity
                                } else {
                                    selectedItems - activity
                                }
                                PreferencesUtil.saveSelectedRelaxItems(context, selectedItems)
                            }
                        )
                    }
                } else {
                    // Outlined clickable card for non-editing mode
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedItems = selectedItems - activity
                                PreferencesUtil.saveSelectedRelaxItems(context, selectedItems)
                            },
                        shape = MaterialTheme.shapes.medium,
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            text = activity,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        // Save Changes
        if (isEditing) {
            DisposableEffect(selectedItems) {
                PreferencesUtil.saveSelectedRelaxItems(context, selectedItems)
                onDispose { }
            }
        }
    }
}