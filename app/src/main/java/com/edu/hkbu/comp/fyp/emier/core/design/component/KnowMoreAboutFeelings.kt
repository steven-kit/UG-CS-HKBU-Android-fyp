package com.edu.hkbu.comp.fyp.emier.core.design.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.edu.hkbu.comp.fyp.emier.navigation.NavDestination
import com.edu.hkbu.comp.fyp.emier.utils.ProgressUtil

@Composable
fun KnowMoreAboutYourFeelings(fontSize: TextUnit, navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val progressMap = mapOf(
        "憤怒" to ProgressUtil.getProgress(context, "Anger"),
        "焦慮" to ProgressUtil.getProgress(context, "Anxiety"),
        "抑鬱" to ProgressUtil.getProgress(context, "Depression"),
        "失望" to ProgressUtil.getProgress(context, "Disappointment"),
        "内疚" to ProgressUtil.getProgress(context, "Guilt"),
        "孤獨" to ProgressUtil.getProgress(context, "Lonely"),
        "緊張" to ProgressUtil.getProgress(context, "Nervous")
    )

    progressMap.forEach { (key, value) ->
        Log.d("ProgressMap", "$key: $value")
    }

    Text(text = "認識你的情緒多一點",
        fontSize = fontSize,
        modifier = modifier.padding(8.dp),
        style = MaterialTheme.typography.labelLarge
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(listOf(
            "憤怒" to NavDestination.Anger,
            "焦慮" to NavDestination.Anxiety,
            "抑鬱" to NavDestination.Depression,
            "失望" to NavDestination.Disappointment,
            "内疚" to NavDestination.Guilt,
            "孤獨" to NavDestination.Lonely,
            "緊張" to NavDestination.Nervous
        )) { (feeling, destination) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                FeelingIcon(fontSize, feeling, destination, navController)
                CircularProgressIndicator(
                    progress = progressMap[feeling]?.coerceIn(0f, 1f) ?: 0f,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun FeelingIcon(fontSize: TextUnit, feeling: String, destination: NavDestination, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate(destination.route) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(destination.imageResId!!),
            contentDescription = feeling,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(text = feeling, fontSize = fontSize,
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
        )
    }
}