package com.edu.hkbu.comp.fyp.emier.core.design.component

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel

@Composable
fun ReusableVideoPlayer(
    videoResId: Int,
    playerViewModel: VideoPlayerViewModel,
    context: Context
) {
    var isPlaying by remember { mutableStateOf(false) }
    playerViewModel.mediaItem = videoResId

    Box(modifier = Modifier.fillMaxWidth()) {
        if (isPlaying) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { playerViewModel.playerViewBuilder(context) }
            )
        }
    }

    LaunchedEffect(videoResId) {
        isPlaying = true
        playerViewModel.apply {
            releasePlayer()
            initializePlayer(context)
            playVideo(context)
        }
    }
}