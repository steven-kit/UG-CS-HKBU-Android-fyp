package com.example.myapplication.feelings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import kotlinx.coroutines.launch

data class Section(val title: String, val content: String, val items: List<Pair<String, String>> = emptyList())

@Composable
fun AnxietyScreen() {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val sections = listOf(
        Section( stringResource(R.string.what_is_anxiety),
            stringResource(R.string.anxiety_intro),
            listOf(
                stringResource(R.string.symptoms_physiological) to stringResource(R.string.anxiety_symptoms_physiological),
                stringResource(R.string.symptoms_emotional) to stringResource(R.string.anxiety_symptoms_emotional),
                stringResource(R.string.symptoms_cognitive) to stringResource(R.string.anxiety_symptoms_cognitive),
                stringResource(R.string.symptoms_behavioral) to stringResource(R.string.anxiety_symptoms_behavioral)
            )
        ),
        Section( "思想陷阱"
            , stringResource(R.string.anxiety_intro2),
            listOf(
                stringResource(R.string.trap1) to stringResource(R.string.trap1_content),
                stringResource(R.string.trap2) to stringResource(R.string.trap2_content),
                stringResource(R.string.trap3) to stringResource(R.string.trap3_content),
                stringResource(R.string.trap4) to stringResource(R.string.trap4_content),
                stringResource(R.string.trap5) to stringResource(R.string.trap5_content),
                stringResource(R.string.trap6) to stringResource(R.string.trap6_content),
                stringResource(R.string.trap7) to stringResource(R.string.trap7_content),
                stringResource(R.string.trap8) to stringResource(R.string.trap8_content),
                stringResource(R.string.trap9) to stringResource(R.string.trap9_content),
                stringResource(R.string.trap10) to stringResource(R.string.trap10_content),
            )),
        Section( "我的思想陷阱類型" , stringResource(R.string.anxiety_intro3)),
        Section( "故事" , stringResource(R.string.anxiety_story)),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            TextButton(onClick = { expanded = true }) {
                Text(text = sections[selectedIndex].title)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                sections.forEachIndexed { index, section ->
                    DropdownMenuItem(
                        text = { Text(text = section.title) },
                        onClick = {
                        selectedIndex = index
                        expanded = false
                        coroutineScope.launch {
                            listState.animateScrollToItem(index)
                        }
                    })
                }
            }
        }
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(sections) { _, section ->
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = section.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(text = section.content, fontSize = 16.sp, color = Color.DarkGray)
                    LazyRow() {
                        itemsIndexed(section.items) { _, item ->
                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(240.dp)
                                    .height(160.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = item.first, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                                    Text(text = item.second, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnxietyScreenPreview() {
    AnxietyScreen()
}