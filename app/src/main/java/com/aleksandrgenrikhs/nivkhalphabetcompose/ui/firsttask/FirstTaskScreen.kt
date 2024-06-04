package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.firsttask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorPrimary

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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            CardLetter(
                progress = uiState.progressLetter,
                letter = uiState.selectedLetter,
                onClick = {
                    viewModel.onClickElement(LETTER)
                },
                isClickable = uiState.isClickableLetter,
            )
        }
        item {
            CardWord(
                progress = uiState.progressFirstWord,
                title = uiState.wordTitle1,
                icon = uiState.wordIcon1,
                onClick = {
                    viewModel.onClickElement(WORD1)
                },
                isClickable = uiState.isClickableFirstWord,
                isVisible = uiState.isVisibleFirstWord,
                getWordError = uiState.getWordError
            )
        }
        item {
            CardWord(
                progress = uiState.progressSecondWord,
                title = uiState.wordTitle2,
                icon = uiState.wordIcon2,
                onClick = {
                    viewModel.onClickElement(WORD2)
                },
                isClickable = uiState.isClickableSecondWord,
                isVisible = uiState.isVisibleSecondWord,
                getWordError = uiState.getWordError

            )
        }
        item {
            CardWord(
                progress = uiState.progressThirdWord,
                title = uiState.wordTitle3,
                icon = uiState.wordIcon3,
                onClick = {
                    viewModel.onClickElement(WORD3)
                },
                isClickable = uiState.isClickableThirdWord,
                isVisible = uiState.isVisibleThirdWord,
                getWordError = uiState.getWordError

            )
        }
    }
    if (uiState.navigate) {
        navController.popBackStack(
            "${NavigationDestination.TasksScreen.destination}/$letter",
            inclusive = false
        )
    }
}