package com.edu.hkbu.comp.fyp.emier.screen

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
class VideoPlayerViewModel: ViewModel() {
    private var exoPlayer: ExoPlayer? = null
    var mediaItem: Int? = null

    fun initializePlayer(context: Context) {
        exoPlayer = ExoPlayer.Builder(context).build()
    }

    fun releasePlayer() {
        exoPlayer?.playWhenReady = false
        exoPlayer?.release()
        exoPlayer = null
    }

    fun playVideo(context: Context) {
        exoPlayer?.let { player ->
            player.apply {
                stop()
                clearMediaItems()
                setMediaItem(MediaItem.fromUri(
                    "android.resource://${context.packageName}/${mediaItem}"
                ))
                prepare()
                playWhenReady = true
                play()
            }
        }
    }

    fun playerViewBuilder(context: Context): PlayerView {
        val activity = context as Activity
        val playerView = PlayerView(context).apply {
            player = exoPlayer
            controllerAutoShow = true
            keepScreenOn = true
            setFullscreenButtonClickListener { isFullScreen ->
                if (isFullScreen) {
                    activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
                } else {
                    activity.requestedOrientation = SCREEN_ORIENTATION_USER
                }
            }
        }
        return playerView
    }
}