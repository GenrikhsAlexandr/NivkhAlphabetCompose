package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.RevisionSecondLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.RevisionSecondViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun RevisionSecondScreen(
    navController: NavController,
    viewModel: RevisionSecondViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = null, block = { viewModel.getWords() })

    with(uiState) {
        RevisionSecondLayout(
            words = words,
            wordsId = wordsId,
            icon = icon,
            correctWordId = correctWordId,
            onWordClick = (viewModel::checkUserGuess),
            onIconClick = (viewModel::playSound),
            isCorrectAnswer = isCorrectAnswers,
            isClickable = !isUserAnswerCorrect,
        )
        if (isCompleted) {
            val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_revision2)
            var showDialog by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = null) {
                delay(2000)
                showDialog = true
            }
            if (showDialog) {
                if (!isFinishAudio) {
                    viewModel.playSound(Constants.FINISH_AUDIO)
                }
                Dialog(
                    navigationBack = {
                        navController.popBackStack(
                            NavigationDestination.RevisionTaskScreen.destination,
                            inclusive = false
                        )
                    },
                    navigationNext = {
                        navController.navigate(
                            NavigationDestination.LettersScreen.destination
                        ) {
                            popUpTo(NavigationDestination.RevisionSecondScreen.destination) {
                                inclusive = true
                            }
                        }
                    },
                    painter = painter,
                    title = stringResource(id = R.string.textEndRevisionSecond),
                    textButtonBack = stringResource(id = R.string.backRevisionTasks),
                    textButtonNext = stringResource(id = R.string.backAlphabet),
                    isVisibleSecondButton = true,
                    onDismissRequest = {}
                )
            }
        }
    }
}