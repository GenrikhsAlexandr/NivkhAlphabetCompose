package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.ButtonSecondTask
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TextSecondTask
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.SecondTaskViewModel

@Composable
fun SecondTaskScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SecondTaskViewModel = hiltViewModel(),
    letter: String,
) {
    viewModel.setLetter(letter)
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getWords(letter)
    })

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.words.isNotEmpty()) {
        println("icon0 = ${uiState.words[0].icon}")
        println("icon1 = ${uiState.words[1].icon}")
        println("icon2 = ${uiState.words[1].icon}")

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary)
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            TextSecondTask(
                letter = letter
            )
            ButtonSecondTask(
                onClick = {
                    viewModel.flipCard(uiState.words[0].wordId, uiState.words[0].letterId)
                },
                icon = uiState.words[0].icon,
                title = uiState.words[0].title,
                isFlipped = uiState.words[0].isFlipped,
                isClickable = !uiState.isPlaying && !uiState.isAnswerCorrect,
                isRightAnswer = uiState.words[0].isRightAnswer,

            )
            ButtonSecondTask(
                onClick = {
                    viewModel.flipCard(uiState.words[1].wordId, uiState.words[1].letterId)

                },
                icon = uiState.words[1].icon,
                title = uiState.words[1].title,
                isFlipped = uiState.words[1].isFlipped,
                isClickable = !uiState.isPlaying && !uiState.isAnswerCorrect,
                isRightAnswer = uiState.words[1].isRightAnswer,
            )
            ButtonSecondTask(
                onClick = {
                    viewModel.flipCard(uiState.words[2].wordId, uiState.words[2].letterId)

                },
                icon = uiState.words[2].icon,
                title = uiState.words[2].title,
                isFlipped = uiState.words[2].isFlipped,
                isClickable = !uiState.isPlaying && !uiState.isAnswerCorrect,
                isRightAnswer = uiState.words[2].isRightAnswer,
            )
            if (uiState.isCompleted && !uiState.isPlaying) {
                val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task2)
                Dialog(
                    navigationBack = {
                        navController.popBackStack(
                            NavigationDestination.LettersScreen.destination,
                            inclusive = false
                        )
                    },
                    navigationNext = {
                        navController.navigate(
                            "${NavigationDestination.ThirdTaskScreen.destination}/$letter"
                        )
                    },
                    painter = painter,
                    title = stringResource(id = R.string.supper),
                    textButtonBack = stringResource(id = R.string.backAlphabet),
                    textButtonNext = stringResource(id = R.string.nextTask, Task.THIRD.stableId),
                )
            }
        }
    }
}