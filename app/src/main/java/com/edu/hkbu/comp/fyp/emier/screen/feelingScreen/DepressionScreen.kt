package com.edu.hkbu.comp.fyp.emier.screen.feelingScreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.core.design.component.ABCTechniquesScreen
import com.edu.hkbu.comp.fyp.emier.core.design.component.PageContent
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel
import com.edu.hkbu.comp.fyp.emier.utils.ProgressUtil


@Composable
fun DepressionScreen(fontSize: TextUnit) {
    val context = LocalContext.current

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
        Section(R.string.story, R.string.depression_story, R.raw.depression_story),
        Section(R.string.analysis, R.string.depression_analysis_detail),
        Section(R.string.vicious_circle, R.string.depression_vicious_circle_content, imageId = R.drawable.depression_cycle),
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
        if (page == sections.lastIndex) {
            ABCTechniquesScreen(fontSize)
        } else {
            val section = sections[page]
            val playerViewModel = remember { VideoPlayerViewModel() }
            PageContent(
                titleId = section.titleId,
                contentId = section.contentId,
                videoId = section.videoId,
                items = section.items,
                imageId = section.imageId,
                context = context,
                playViewModel = playerViewModel,
                pagerState = pagerState,
                currentPage = page
            )
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val progress = (pagerState.currentPage + 1) / sections.size.toFloat()
        ProgressUtil.saveProgress(context, "Depression", progress)
    }
}