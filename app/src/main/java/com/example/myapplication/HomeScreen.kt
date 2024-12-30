package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.Routes
import com.example.myapplication.components.KnowMoreAboutYourFeelings

@Composable
fun HomeScreen(navController: NavHostController){
    Column() {
        Greeting()
        RelaxCard(navController = navController)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}