package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
    viewModel: RevisionSecondViewModel = hiltViewModel(),
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.getWords()
            isLaunchedEffectCalled = true
        }
    }

    with(uiState) {
        RevisionSecondLayout(
            words = title,
            wordsId = wordsId,
            icon = icon,
            correctWordId = correctWordId,
            onWordClick = (viewModel::checkUserGuess),
            onIconClick = (viewModel::playSound),
            isCorrectAnswer = isCorrectAnswers,
            isClickable = !isUserAnswerCorrect,
            onDividerVisibilityChange = onDividerVisibilityChange
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
                        onDividerVisibilityChange(false)
                        navController.popBackStack(
                            NavigationDestination.LettersScreen.destination,
                            inclusive = false
                        )
                    },
                    navigationNext = {
                        onDividerVisibilityChange(false)
                        navController.navigate(
                            NavigationDestination.RevisionThirdScreen.destination
                        ) {
                            popUpTo(NavigationDestination.RevisionSecondScreen.destination) {
                                inclusive = true
                            }
                        }
                    },
                    painter = painter,
                    title = stringResource(id = R.string.textEndRevisionSecond),
                    textButtonBack = stringResource(id = R.string.backAlphabet),
                    textButtonNext = stringResource(id = R.string.nextRevisionTasks),
                    isVisibleSecondButton = true,
                    onDismissRequest = {}
                )
            }
        }
    }
}