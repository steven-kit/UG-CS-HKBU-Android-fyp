package com.edu.hkbu.comp.fyp.emier.core.design.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.edu.hkbu.comp.fyp.emier.screen.Video
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel

@Composable
fun VideoDetailScreen(video: Video, playerViewModel: VideoPlayerViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = video.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ReusableVideoPlayer(
            videoResId = video.videoResId,
            playerViewModel = playerViewModel,
            context = context
        )
    }
}