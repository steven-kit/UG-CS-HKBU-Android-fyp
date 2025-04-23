package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun ACCEPTSTechniquesScreen(fontSize: TextUnit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            ) {
                Text(
                    text = "ACCEPTS 技巧",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // Introduction
        item {
            Text(
                text = "ACCEPTS技巧是辨證行為治療中幫助個人用七個不同的方法來應對壓力帶來的情緒困擾。通過分散注意力，以減低當刻情緒的強度。不妨試試通過反覆練習，找出適合自己的方法。",
                fontSize = fontSize,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Techniques
        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "A – Activities 活動",
                content = "進行一些活動來分散注意力。\n例如：打電話給朋友；觀看喜愛的電影或電視節目；打機；寫日記；整理房間；散步或跑步；進行強度運動；閱讀書籍；聆聽音樂；上網；煮喜歡吃的食物。",
                icon = Icons.Default.DateRange
            )
        }

        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "C – Contributing 貢獻",
                content = "通過參與一些有意義的活動，為他人做出貢獻以分散注意力。\n例如：幫助朋友或兄弟姊妹辦事；為他人製作精美的小手工；捐贈不需要的物品給有需要的人；給身邊人一個大大的擁抱；參加義工活動。",
                icon = Icons.Default.Create
            )
        }

        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "C – Comparisons 比較",
                content = "將自己的景況與比自己不幸的人景況作比較。\n例如：將你現在的感受與你處於更差境況時的感受進行比較；思考那些與你處境相同或比自己處境更差的人。",
                icon = Icons.Default.Person
            )
        }

        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "E – Emotions 情緒",
                content = "通過體驗不同的情緒，轉移注意力。\n例如：觀看有趣的電視節目或感人的電影；聆聽輕鬆或動感的音樂；到商店閱讀笑話書藉。",
                icon = Icons.Default.Face
            )
        }

        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "P – Pushing Away 推開遠離",
                content = "運用推開遠離的策略，讓自己遠離焦慮情緒。\n例如：通過轉移你的注意力和思維來暫時離開情況，試想像在你和現在面對的景況之間建立一道想像的牆壁，將自己與現況分隔開；想像將焦慮放入盒子中，放在架子上一段時間。",
                icon = Icons.Default.Delete
            )
        }

        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "T – Thoughts 思緒轉移",
                content = "想一些其他的事情來替換你的現有的思緒。\n例如：閱讀；拼圖；數讀；研究海報上的顏色、牆壁上的瓷磚顏色／數量或任何其他東西；在腦海中重複歌詞。",
                icon = Icons.Default.ArrowForward
            )
        }

        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "S – Sensations 感統",
                content = "強化身體其他感覺，如觸覺、味覺、聽覺、視覺、嗅覺。\n例如：咬冰塊；聆聽大聲音樂：冼溫水或冷水浴：擠壓壓力球；做仰臥起坐或掌上壓；摸摸你的狗和貓。",
                icon = Icons.Default.Build
            )
        }
    }
}