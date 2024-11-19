package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary


@Composable
fun SplashScreen(
    navController: NavController,
) {
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        navigation(navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary)
            .scale(scale.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_start_alphabet),
            contentDescription = "Splash Screen",
            modifier = Modifier
                .height(200.dp)
                .wrapContentWidth()
        )
        Text(
            text = stringResource(id = R.string.letsGo),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

private fun navigation(navController: NavController) {
    navController.navigate(NavigationDestination.LettersScreen.destination) {
        popUpTo(0) {
            inclusive = true
        }
    }
}