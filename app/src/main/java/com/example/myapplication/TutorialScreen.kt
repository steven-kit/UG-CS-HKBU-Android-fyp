package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.components.KnowMoreAboutYourFeelings
import kotlinx.coroutines.launch

@Composable
fun TutorialScreen(navController: NavHostController) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> IntroductionPage(onNextClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(1) }
            })
            1 -> SurveyFormPage(
                onPreviousClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(0) }
                },
                onNextClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(2) }
                }
            )
            2 -> GraphPage(onPreviousClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(1) }
            }, navController)
        }
    }
}

@Composable
fun IntroductionPage(onNextClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "引言",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.tutorial_intro),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onNextClick, modifier = Modifier.align(Alignment.End)) {
            Text("Next")
        }
    }
}

@Composable
fun SurveyFormPage(onPreviousClick: () -> Unit, onNextClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.tutorial_intro2),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Survey form implementation
        SurveyForm()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(onClick = onPreviousClick) {
                Text("Previous")
            }
            Button(onClick = onNextClick) {
                Text("Next")
            }
        }
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
fun GraphPage(onPreviousClick: () -> Unit, navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.tutorial_intro3),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        KnowMoreAboutYourFeelings(navController)
        Button(onClick = onPreviousClick, modifier = Modifier
            .align(Alignment.Start)
            .padding(top = 16.dp)) {
            Text("Previous")
        }
    }
}