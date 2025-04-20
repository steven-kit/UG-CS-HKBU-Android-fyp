package com.edu.hkbu.comp.fyp.emier.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.utils.ExpandableAnnotatedText
import com.edu.hkbu.comp.fyp.emier.utils.PreferencesUtil


@Composable
fun GuideScreen(navController: NavController, playerViewModel: VideoPlayerViewModel) {
    val pagerState = rememberPagerState(pageCount = {
        4
    })

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            when (page) {
                0 -> {
                    GuidePage1(R.raw.introduction, playerViewModel, context)
                }

                1 -> {
                    GuidePage2()
                }

                2 -> {
                    GuidePage3()
                }

                3 -> {
                    GuidePage4(navController, R.raw.cbt, playerViewModel, context)
                }
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}

@Composable
fun GuidePage1(
    videoResId: Int,
    playerViewModel: VideoPlayerViewModel,
    context: Context
) {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    playerViewModel.mediaItem = videoResId

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        VideoPlayer(viewModel = playerViewModel,
            isPlaying = isPlaying,
            onPlayerClosed = { isVideoPlaying ->
                isPlaying = isVideoPlaying
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableAnnotatedText(id = R.string.splash_page1, fontSize = 16.sp)

        LaunchedEffect(key1 = playerViewModel.mediaItem) {
            isPlaying = true
            playerViewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo(context)
            }
        }
    }
}

@Composable
fun VideoPlayer(
    viewModel: VideoPlayerViewModel,
    isPlaying: Boolean,
    onPlayerClosed: (isVideoPlaying: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.mediaItem = R.raw.introduction
            }
    ) {
        if (isPlaying) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    viewModel.playerViewBuilder(context)
                }
            )
        }
    }
}

@Composable
fun GuidePage2() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.splash_page2),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Survey form implementation
        SurveyForm()
    }
}

@Composable
fun SurveyForm() {
    val options = listOf("Very Bad", "Bad", "Medium", "Good", "Very Good")
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(selected = false, onClick = { /* Handle selection */ })
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun GuidePage3() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.splash_page3),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun GuidePage4(
    navController: NavController,
    videoResId: Int,
    playerViewModel: VideoPlayerViewModel,
    context: Context
) {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    playerViewModel.mediaItem = videoResId

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.splash_page4_title),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        VideoPlayer(viewModel = playerViewModel,
            isPlaying = isPlaying,
            onPlayerClosed = { isVideoPlaying ->
                isPlaying = isVideoPlaying
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableAnnotatedText(id = R.string.splash_page4, fontSize = 16.sp)
        Button(
            onClick = {
                PreferencesUtil.setHasSeenGuide(context)
                navController.navigate("home")
                      },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("開始")
        }

        LaunchedEffect(key1 = playerViewModel.mediaItem) {
            isPlaying = true
            playerViewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo(context)
            }
        }
    }
}