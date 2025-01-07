package com.example.myapplication.feelingScreens

import android.content.ClipData
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.components.QuestionCard
import kotlinx.coroutines.launch
import com.example.myapplication.helper.annotatedStringResource

data class Section(@StringRes val titleId: Int, @StringRes val contentId: Int, val items: List<Pair<Int, Int>> = emptyList(), @DrawableRes val imageId: Int = 0)

@Composable
fun AnxietyScreen() {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val sections = listOf(
        Section( R.string.what_is_anxiety, R.string.anxiety_intro,
            listOf(
                R.string.symptoms_physiological to R.string.anxiety_symptoms_physiological,
                R.string.symptoms_emotional to R.string.anxiety_symptoms_emotional,
                R.string.symptoms_cognitive to R.string.anxiety_symptoms_cognitive,
                R.string.symptoms_behavioral to R.string.anxiety_symptoms_behavioral
            )
        ),
        Section( R.string.automatic_thoughts, R.string.anxiety_intro2,
            listOf(
                R.string.trap1 to R.string.trap1_content,
                R.string.trap2 to R.string.trap2_content,
                R.string.trap3 to R.string.trap3_content,
                R.string.trap4 to R.string.trap4_content,
                R.string.trap5 to R.string.trap5_content,
                R.string.trap6 to R.string.trap6_content,
                R.string.trap7 to R.string.trap7_content,
                R.string.trap8 to R.string.trap8_content,
                R.string.trap9 to R.string.trap9_content,
                R.string.trap10 to R.string.trap10_content,
            )),
        Section( R.string.my_automatic_thoughts, R.string.anxiety_intro3 ),
        Section( R.string.story, R.string.anxiety_story ),
        Section( R.string.analysis, R.string.analysis_detail ),
        Section( R.string.vicious_circle, R.string.vicious_circle_content, imageId = R.drawable.anxiety_cycle),
        Section( R.string.cbt, R.string.cbt_content )
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
                Text(annotatedStringResource(sections[selectedIndex].titleId))
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                sections.forEachIndexed { index, section ->
                    DropdownMenuItem(
                        text = { Text(annotatedStringResource(section.titleId)) },
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
                Column(modifier = Modifier
                    .padding(4.dp)
                ) {
                    Text(
                        text = annotatedStringResource(section.titleId),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        textAlign = TextAlign.Center
                    )
                    if (section.contentId != 0) {
                        Text(
                            annotatedStringResource(section.contentId),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            color = Color.DarkGray
                        )
                    }
                    if (section.titleId == R.string.analysis) {
                        DraggableText(annotatedStringResource(R.string.anxiety_story))
                        QuestionCard(title = "身體反應", question = "每天下班回家路上致電琪琪媽媽問當天的功課", answer = "面紅耳赤、手心冒汗、心跳加快")
                    }
                    LazyRow() {
                        itemsIndexed(section.items) { _, item ->
                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(240.dp)
                                    .height(160.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(annotatedStringResource(item.first), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                                    Text(annotatedStringResource(item.second), fontSize = 14.sp)
                                }
                            }
                        }
                    }
                    if (section.imageId != 0) {
                        Image(
                            painter = painterResource(id = section.imageId),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun DraggableText(annotatedString: AnnotatedString) {
    val draggableWords = annotatedString.getStringAnnotations("draggable", 0, annotatedString.length)
    Column {
        LazyRow {
            items(draggableWords) { word ->
                DraggableWord(word.item)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableWord(word: String) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.LightGray)
            .dragAndDropSource {
                detectTapGestures( onLongPress = {
                    startTransfer(
                        DragAndDropTransferData(
                            ClipData.newPlainText("text", word),
                            flags = View.DRAG_FLAG_GLOBAL
                        )
                    )
                }
            ) }
    ) {
        Text(text = word, modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AnxietyScreenPreview() {
    AnxietyScreen()
}