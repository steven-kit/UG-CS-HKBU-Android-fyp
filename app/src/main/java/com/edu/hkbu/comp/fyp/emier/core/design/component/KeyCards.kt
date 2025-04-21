package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.hkbu.comp.fyp.emier.utils.annotatedStringResource

@Composable
fun KeyCards(items: List<Pair<Int, Int>>) {
    LazyRow {
        itemsIndexed(items) { _, item ->
            OutlinedCard(
                modifier = Modifier
                    .padding(4.dp)
                    .width(240.dp)
                    .height(160.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        annotatedStringResource(item.first),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(annotatedStringResource(item.second), fontSize = 14.sp)
                }
            }
        }
    }
}
