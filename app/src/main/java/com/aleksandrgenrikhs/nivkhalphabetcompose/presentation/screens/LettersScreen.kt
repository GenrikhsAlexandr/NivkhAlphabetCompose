package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.LettersLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.LettersViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASKS_SCREEN


@Composable
fun LetterScreen(
    navController: NavController,
    viewModel: LettersViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    viewModel.isLetterCompleted()
    with(uiState) {
        LettersLayout(
            letters = letters,
            onClick = { letter ->
                navController.navigate("${TASKS_SCREEN}/$letter")
            },
            isVisibleRepeat = isVisibleRepeat
        )
    }
}