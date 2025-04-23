package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun ABCTechniquesScreen(fontSize: TextUnit) {
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
                    text = "辯證行為治療的ABC技巧",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // Introduction
        item {
            Text(
                text = "每當我們處於身體或環境壓力之下時，都容易出現情緒反應。當我們情緒高漲的狀態下做出決定和行為，往往是受情緒影響而並非基於邏輯或理智思維而做。",
                fontSize = fontSize,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // ABC Techniques
        item {
            Text(
                text = "ABC技巧如下:",
                fontSize = fontSize,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Technique A
        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "A – Accumulating positive experiences 累積正面經驗",
                content = "要與負面情緒抗衡，短期目標是要增加及累積正面情緒。\n\n• 多做令心情愉悅並可帶來正面情緒的事情\n• 列出一個個人化愉悅活動清單，然後每天做一件清單上的活動。(參考附件一)",
                icon = Icons.Default.ThumbUp
            )
        }

        // Technique B
        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "B – Building mastery 建立專業技能",
                content = "建立專業技能是指參與一些困難但不是不可能完成的活動的技巧。\n\n• 計劃每天至少做一件事情，讓自己感到有能力和可掌控自己的生活。\n• 計劃可成功的事而非失敗的事。做一些困難但不是不可能的事。\n• 隨著時間過去，逐漸增加困難程度。如果第一個任務太困難，下次做一些稍微容易的事情。",
                icon = Icons.Default.Star
            )
        }

        // Technique C
        item {
            TechniqueCard(
                fontSize = fontSize,
                title = "C – Coping ahead with difficult situations 提前應對困難情況",
                content = "提前應對困難情況是利用想像練習的技巧，來幻想如果這些情緒出現時如何應對，來增強應對困難情緒的能力。\n\n1. 描述一個可能引起負面情緒的情況\n2. 決定您想在該情況下使用的應對或解決問題的方法\n3. 盡可能貼近現實地在腦內想像情況\n4. 在腦內排練一下有效的應對方法",
                icon = Icons.Default.Warning
            )
        }
    }
}

@Composable
fun TechniqueCard(fontSize: TextUnit,title: String, content: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = fontSize,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                fontSize = fontSize,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}