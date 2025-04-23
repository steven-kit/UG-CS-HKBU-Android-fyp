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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayer
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel
import com.edu.hkbu.comp.fyp.emier.utils.ExpandableAnnotatedText
import com.edu.hkbu.comp.fyp.emier.utils.PreferencesUtil
import com.edu.hkbu.comp.fyp.emier.utils.annotatedStringResource
import com.edu.hkbu.comp.fyp.emier.viewModel.QuestionViewModel

@Composable
fun PageContent(
    @StringRes titleId: Int,
    @StringRes contentId: Int,
    @RawRes videoId: Int,
    items: List<Pair<Int, Int>>,
    @DrawableRes imageId: Int,
    playViewModel: VideoPlayerViewModel,
    context: Context,
    pagerState: PagerState,
    currentPage: Int
) {
    val fontSize = PreferencesUtil.getFontSize(context).sp

    val questionViewModel: QuestionViewModel = viewModel()

    var isPlaying by remember { mutableStateOf(false) }
    playViewModel.mediaItem = videoId

    val isCurrentPage = pagerState.currentPage == currentPage

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
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Video Player
        if (videoId != 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp)
            ) {
                VideoPlayer(viewModel = playViewModel,
                    isPlaying = isPlaying,
                    onPlayerClosed = { isVideoPlaying ->
                        isPlaying = isVideoPlaying
                    }
                )
            }
        }

        // Content
        if (contentId != 0) {
            ExpandableAnnotatedText(id = contentId, fontSize = fontSize,)
        }

        // Survey
        if (titleId == R.string.my_automatic_thoughts) {
            Survey(context = context)
        }

        // Draggable text
        if (titleId == R.string.analysis) {
            when (contentId) {
                R.string.anxiety_analysis_detail -> DragAndDrop(fontSize, questionViewModel, R.string.anxiety_story)
                R.string.depression_analysis_detail -> DragAndDrop(fontSize, questionViewModel, R.string.depression_story)
            }
        }

        // Key cards
        KeyCards(fontSize, items)

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

    DisposableEffect(Unit) {
        onDispose {
            playViewModel.releasePlayer()
        }
    }

    LaunchedEffect(isCurrentPage) {
        if (isCurrentPage && videoId != 0) {
            isPlaying = true
            playViewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo(context)
            }
        } else {
            isPlaying = false
            playViewModel.releasePlayer()
        }
    }
}