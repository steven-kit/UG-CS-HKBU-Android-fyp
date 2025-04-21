package com.edu.hkbu.comp.fyp.emier.screen.feelingScreen

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.core.design.component.PageContent
import com.edu.hkbu.comp.fyp.emier.screen.VideoPlayerViewModel
import com.edu.hkbu.comp.fyp.emier.utils.ProgressUtil

data class Section(@StringRes val titleId: Int, @StringRes val contentId: Int, @RawRes val videoId: Int = 0, val items: List<Pair<Int, Int>> = emptyList(), @DrawableRes val imageId: Int = 0)

@Composable
fun AnxietyScreen() {
    val context = LocalContext.current
    val playerViewModel: VideoPlayerViewModel = viewModel()

    val sections = listOf(
        Section(R.string.what_is_anxiety, R.string.anxiety_intro,
            items = listOf(
                R.string.symptoms_physiological to R.string.anxiety_symptoms_physiological,
                R.string.symptoms_emotional to R.string.anxiety_symptoms_emotional,
                R.string.symptoms_cognitive to R.string.anxiety_symptoms_cognitive,
                R.string.symptoms_behavioral to R.string.anxiety_symptoms_behavioral
            )
        ),
        Section(R.string.automatic_thoughts, R.string.anxiety_intro2,
            items = listOf(
                R.string.trap1 to R.string.trap1_content,
                R.string.trap2 to R.string.trap2_content,
                R.string.trap3 to R.string.trap3_content,
                R.string.trap4 to R.string.trap4_content,
                R.string.trap5 to R.string.trap5_content,
                R.string.trap6 to R.string.trap6_content,
                R.string.trap7 to R.string.trap7_content,
                R.string.trap8 to R.string.trap8_content,
                R.string.trap9 to R.string.trap9_content,
                R.string.trap10 to R.string.trap10_content,
            )),
        Section(R.string.my_automatic_thoughts, R.string.anxiety_intro3),
        Section(R.string.story, R.string.anxiety_story),
        Section(R.string.analysis, R.string.analysis_detail),
        Section(R.string.vicious_circle, R.string.vicious_circle_content, imageId = R.drawable.anxiety_cycle),
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
        PageContent(titleId = section.titleId,
            contentId = section.contentId,
            videoId = section.videoId,
            items = section.items,
            imageId = section.imageId,
            context = context, playViewModel = playerViewModel,
            pagerState = pagerState,
            currentPage = page
            )
    }

    LaunchedEffect(pagerState.currentPage) {
        val progress = (pagerState.currentPage + 1) / sections.size.toFloat()
        ProgressUtil.saveProgress(context, "Anxiety", progress)
    }
}