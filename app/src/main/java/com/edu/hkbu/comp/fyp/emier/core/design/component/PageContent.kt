package com.edu.hkbu.comp.fyp.emier.core.design.component

import android.content.ClipData
import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayer
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel
import com.edu.hkbu.comp.fyp.emier.utils.ExpandableAnnotatedText
import com.edu.hkbu.comp.fyp.emier.utils.annotatedStringResource

@Composable
fun PageContent(
    @StringRes titleId: Int,
    @StringRes contentId: Int,
    @RawRes videoId: Int,
    items: List<Pair<Int, Int>>,
    @DrawableRes imageId: Int,
    viewModel: VideoPlayerViewModel,
    context: Context
) {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    viewModel.mediaItem = videoId

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Title
        Text(
            text = annotatedStringResource(titleId),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Center
        )
        // Video Player
        if (videoId != 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp)
            ) {
                VideoPlayer(viewModel = viewModel,
                    isPlaying = isPlaying,
                    onPlayerClosed = { isVideoPlaying ->
                        isPlaying = isVideoPlaying
                    }
                )
            }
        }
        // Content
        if (contentId != 0) {
            ExpandableAnnotatedText(id = contentId, fontSize = 16.sp)
        }
        // Draggable text
        if (titleId == R.string.analysis) {
            DraggableText(annotatedStringResource(R.string.anxiety_story))
            QuestionCard(
                title = "身體反應",
                question = "每天下班回家路上致電琪琪媽媽問當天的功課",
                answer = "面紅耳赤、手心冒汗、心跳加快"
            )
        }
        // Key cards
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
        // Any Image to display
        if (imageId != 0) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    LaunchedEffect(key1 = viewModel.mediaItem) {
        if (videoId != 0) {
            isPlaying = true
            viewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo(context)
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
                detectTapGestures(onLongPress = {
                    startTransfer(
                        DragAndDropTransferData(
                            ClipData.newPlainText("text", word),
                            flags = View.DRAG_FLAG_GLOBAL
                        )
                    )
                }
                )
            }
    ) {
        Text(text = word, modifier = Modifier.padding(8.dp))
    }
}