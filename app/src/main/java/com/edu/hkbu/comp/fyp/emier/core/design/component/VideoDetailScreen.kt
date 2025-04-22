package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.edu.hkbu.comp.fyp.emier.screen.Video
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel
import com.edu.hkbu.comp.fyp.emier.utils.ExpandableAnnotatedText

@Composable
fun VideoDetailScreen(fontSize: TextUnit, video: Video, playerViewModel: VideoPlayerViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = video.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ReusableVideoPlayer(
                videoResId = video.videoResId,
                playerViewModel = playerViewModel,
                context = context
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableAnnotatedText(id = video.captionId, fontSize = fontSize)
    }

    DisposableEffect(Unit) {
        onDispose {
            playerViewModel.releasePlayer()
        }
    }
}