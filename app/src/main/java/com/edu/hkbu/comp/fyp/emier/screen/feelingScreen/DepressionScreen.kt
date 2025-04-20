package com.edu.hkbu.comp.fyp.emier.screen.feelingScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.core.design.component.PageContent
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel


@Composable
fun DepressionScreen(playerViewModel: VideoPlayerViewModel) {
    val context = LocalContext.current
    val viewModel: VideoPlayerViewModel = viewModel()

    val sections = listOf(
        Section(
            R.string.what_is_depression, R.string.depression_intro, R.raw.depression_part1,
            listOf(
                R.string.symptoms_physiological to R.string.depression_symptoms_physiological,
                R.string.symptoms_emotional to R.string.depression_symptoms_emotional,
                R.string.symptoms_cognitive to R.string.depression_symptoms_cognitive,
                R.string.symptoms_behavioral to R.string.depression_symptoms_behavioral
            )
        ),
        Section(R.string.automatic_thoughts, R.string.depression_intro2, R.raw.depression_part2),
        Section(R.string.story, R.string.depression_story, R.raw.depression_story)
    )
    val pagerState = rememberPagerState(
        pageCount = {
            sections.size
        }
    )

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = true,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val section = sections[page]
        val pageViewModel = remember { VideoPlayerViewModel() }
        PageContent(
            titleId = section.titleId,
            contentId = section.contentId,
            videoId = section.videoId,
            items = section.items,
            imageId = section.imageId,
            context = context,
            viewModel = pageViewModel,
            pagerState = pagerState,
            currentPage = page
        )
    }
}