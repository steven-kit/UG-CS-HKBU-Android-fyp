package com.edu.hkbu.comp.fyp.emier.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.edu.hkbu.comp.fyp.emier.R
import com.edu.hkbu.comp.fyp.emier.auth.UserViewModel
import com.edu.hkbu.comp.fyp.emier.navigation.Routes
import com.edu.hkbu.comp.fyp.emier.core.design.component.KnowMoreAboutYourFeelings

@Composable
fun HomeScreen(navController: NavHostController, userViewModel: UserViewModel){
    Column() {
        Greeting(userViewModel)
        RelaxCard(navController = navController)
        KnowMoreAboutYourFeelings(navController)
    }
}

@Composable
fun Greeting(userViewModel: UserViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = if (userViewModel.isLoggedIn.value) "Hello" else "Hello, please log in to explore more")
            Image(
                painter = painterResource(id = R.drawable.greeting),
                contentScale = ContentScale.Fit,
                contentDescription = "Relax",
                modifier = Modifier.size(248.dp)
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
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.yoga),
                contentDescription = "Relax",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(86.dp)
                    .padding(8.dp),
            )
            Text(
                text = stringResource(id = R.string.relax),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(18.dp)
            )
            Icon(
                Icons.Filled.ArrowForward, contentDescription = "Arrow Forward",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}