package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.SplashScreenViewModel

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.isLoading.collectAsState()

    if (!uiState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorPrimary),
            contentAlignment = Alignment.Center
        ) {
            val painter = rememberAsyncImagePainter(model = R.drawable.ic_start_alphabet)
            Dialog(
                navigationBack = {},
                navigationNext = {
                    navigation(navController)
                },
                painter = painter,
                title = stringResource(id = R.string.textStartApp),
                textButtonBack = "",
                textButtonNext = stringResource(id = R.string.letsGo),
                isVisibleSecondButton = false,
                onDismissRequest = {
                    navigation(navController)
                }
            )
        }
    }
}

private fun navigation(navController: NavController) {
    navController.navigate(NavigationDestination.LettersScreen.destination) {
        popUpTo(0) {
            inclusive = true
        }
    }
}