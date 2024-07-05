package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.NotConnected
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.ThirdTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.ThirdTaskViewModel

@Composable
fun ThirdTaskScreen(
    navController: NavController,
    viewModel: ThirdTaskViewModel = hiltViewModel(),
    letter: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.setLetter(letter)

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getWords(letter)
    })

    with(uiState) {
        if (!isNetworkConnected) {
            NotConnected(
                navController = navController
            )
        }
        if (words.isNotEmpty()) {
            with(uiState) {
                ThirdTaskLayout(
                    words = words,
                    onIconClick = { viewModel.playSound(it) },
                    onDone = { viewModel.checkMatching("", "") }
                )
            }
        }
    }
}