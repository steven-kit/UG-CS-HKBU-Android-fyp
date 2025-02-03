package com.edu.hkbu.comp.fyp.emier.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.core.design.component.KnowMoreAboutYourFeelings
import kotlinx.coroutines.launch

@Composable
fun TutorialScreen(navController: NavHostController) {

    Text(text = "Tutorial Screen ( video list )")
//    val pagerState = rememberPagerState { 3 }
//    val coroutineScope = rememberCoroutineScope()
//
//    HorizontalPager(
//        state = pagerState,
//        modifier = Modifier.fillMaxSize()
//    ) { page ->
//        when (page) {
//            0 -> IntroductionPage(onNextClick = {
//                coroutineScope.launch { pagerState.animateScrollToPage(1) }
//            })
//            1 -> SurveyFormPage(
//                onPreviousClick = {
//                    coroutineScope.launch { pagerState.animateScrollToPage(0) }
//                },
//                onNextClick = {
//                    coroutineScope.launch { pagerState.animateScrollToPage(2) }
//                }
//            )
//            2 -> GraphPage(onPreviousClick = {
//                coroutineScope.launch { pagerState.animateScrollToPage(1) }
//            }, navController)
//        }
//    }
}

@Composable
fun IntroductionPage(onNextClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Text(
            text = "引言",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.splash_page1),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        IconButton(onClick = onNextClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Filled.ArrowForward,
                contentDescription = "Next")
        }
    }
}

@Composable
fun SurveyFormPage(onPreviousClick: () -> Unit, onNextClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.splash_page2),
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
            IconButton(onClick = onPreviousClick
            ) {
                Icon(Icons.Filled.ArrowBack,
                    contentDescription = "Back")
            }
            IconButton(onClick = onNextClick
            ) {
                Icon(Icons.Filled.ArrowForward,
                    contentDescription = "Next")
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
            text = stringResource(id = R.string.splash_page3),
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        KnowMoreAboutYourFeelings(navController)
        IconButton(onClick = onPreviousClick
        ) {
            Icon(Icons.Filled.ArrowBack,
                contentDescription = "Back")
        }
    }
}