package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.firsttask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.FirstTaskViewModel

@Composable
fun FirstTaskScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FirstTaskViewModel = hiltViewModel(),
    letter: String,
) {
    viewModel.setLetter(letter)
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getWords(letter)
    })

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary)
            .padding(top = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        with(uiState) {
            CardLetter(
                progress = progressLetter,
                title = letter,
                onClick = {
                    viewModel.onClickElement(selectedLetter)
                },
                isClickable = isClickableLetter && !isPlaying,
            )
        }
        if (uiState.words.isNotEmpty()) {
            CardWord(
                progress = uiState.words[0].progress,
                title = uiState.words[0].title,
                icon = uiState.words[0].icon,
                onClick = {
                    viewModel.onClickElement(uiState.words[0].wordId)
                },
                isClickable = uiState.words[0].isClickable && !uiState.isPlaying,
                isVisible = uiState.isVisibleWord,
                getWordError = uiState.getWordError,
            )
            CardWord(
                progress = uiState.words[1].progress,
                title = uiState.words[1].title,
                icon = uiState.words[1].icon,
                onClick = {
                    viewModel.onClickElement(uiState.words[1].wordId)
                },
                isClickable = uiState.words[1].isClickable && !uiState.isPlaying,
                isVisible = uiState.isVisibleWord,
                getWordError = uiState.getWordError,
            )

            CardWord(
                progress = uiState.words[2].progress,
                title = uiState.words[2].title,
                icon = uiState.words[2].icon,
                onClick = {
                    viewModel.onClickElement(uiState.words[2].wordId)
                },
                isClickable = uiState.words[2].isClickable && !uiState.isPlaying,
                isVisible = uiState.isVisibleWord,
                getWordError = uiState.getWordError,
            )
            if (uiState.isCompleted && !uiState.isPlaying) {
                navController.popBackStack(
                    "${NavigationDestination.TasksScreen.destination}/$letter",
                    inclusive = false
                )
            }
        }
    }
}