package com.edu.hkbu.comp.fyp.emier.screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.edu.hkbu.comp.fyp.emier.core.design.component.ExpandableText


@Composable
fun SplashScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = {
        4
    })

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
                    SplashPage1()
                }

                1 -> {
                    SplashPage2()
                }

                2 -> {
                    SplashPage3()
                }

                3 -> {
                    SplashPage4(navController)
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
fun SplashPage1() {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    val viewModel: VideoPlayerViewModel = viewModel()
    viewModel.mediaItem = R.raw.introduction
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        VideoPlayer(viewModel = viewModel,
            isPlaying = isPlaying,
            onPlayerClosed = { isVideoPlaying ->
                isPlaying = isVideoPlaying
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableText(text = stringResource(id = R.string.splash_page1), fontSize = 16.sp)

        LaunchedEffect(key1 = viewModel.mediaItem) {
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
fun SplashPage2() {
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
fun SplashPage3() {
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
fun SplashPage4(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.splash_page4_title),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.splash_page4),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("開始")
        }
    }
}