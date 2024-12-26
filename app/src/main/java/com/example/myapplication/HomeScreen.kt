package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavHostController){
    Column() {
        Greeting()
        RelaxCard(navController = navController)
        Introduction()
        KnowMoreAboutYourFeelings(navController)
    }
}

@Composable
fun Greeting() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Hello, please log in to explore more")
            Image(
                painter = painterResource(id = R.drawable.greeting),
                contentDescription = "Relax",
            )
        }
    }
}

@Composable
fun RelaxCard(modifier: Modifier = Modifier, navController: NavHostController) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            .clickable { navController.navigate(Routes.Relax) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.width(192.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.yoga),
                    contentDescription = "Relax",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(8.dp),
                )
                Text(
                    text = "Relax Yourself",
                    modifier = Modifier.padding(18.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Icon(
                Icons.Filled.ArrowForward, contentDescription = "Arrow Forward",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun Introduction() {
    Text(text = "引言",
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.labelLarge
    )
    Text(text = stringResource(id = R.string.introduction),
        fontSize = 16.sp,
        color = Color.DarkGray
    )
}

@Composable
fun KnowMoreAboutYourFeelings(navController: NavHostController) {
    Text(text = "認識你的情緒多一點",
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.labelLarge
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(listOf(
            "抑鬱" to NavDestination.Depression,
            "憤怒" to NavDestination.Anger,
            "焦慮" to NavDestination.Anxiety,
            "恐懼" to NavDestination.Fear,
            "失望" to NavDestination.Disappointment,
        )) { (feeling, destination) ->
            FeelingIcon(feeling, destination, navController)
        }
    }
}

@Composable
fun FeelingIcon(feeling: String, destination: NavDestination, navController: NavHostController) {
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
        Text(text = feeling,
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}