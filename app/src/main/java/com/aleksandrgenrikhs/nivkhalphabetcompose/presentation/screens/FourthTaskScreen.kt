package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.FourthTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.FourthTaskViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import kotlinx.coroutines.delay

@Composable
fun FourthTaskScreen(
    navController: NavController,
    viewModel: FourthTaskViewModel = hiltViewModel(),
    letter: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.setSelectedLetter(letter)

    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWordsForLetter(letter)
            isLaunchedEffectCalled = true
        }
    }

    with(uiState) {
        FourthTaskLayout(
            onClick = { viewModel.playSound("${WORDS_AUDIO}${wordId}") },
            icon = icon,
            onClickable = isClickable,
            onDone = (viewModel::checkUserGuess),
            isGuessWrong = isGuessWrong,
            onUserGuessChanged = { viewModel.updateUserGuess(it.appendChar(userGuess)) },
            userGuess = userGuess,
            onDelete = { viewModel.deleteLastLetter() },
            onBack = navController::popBackStack
        )
        if (isCompleted) {
            val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task4)
            var showDialog by rememberSaveable { mutableStateOf(false) }
            LaunchedEffect(key1 = null) {
                delay(2000)
                showDialog = true
            }
            if (showDialog) {
                if (shouldPlayFinishAudio) {
                    viewModel.playSound(FINISH_AUDIO)
                }
                DialogSuccess(
                    navigationBack = {
                        navController.popBackStack(
                            NavigationDestination.LettersScreen.destination,
                            inclusive = false
                        )
                    },
                    navigationNext = {
                        navController.navigate(
                            "${NavigationDestination.TasksScreen.destination}/$letter"
                        ) {
                            popUpTo("${NavigationDestination.TasksScreen.destination}/$letter") {
                                inclusive = true
                            }
                        }
                    },
                    painter = painter,
                    title = stringResource(id = R.string.textEndFourthTask),
                    textButtonBack = stringResource(id = R.string.backAlphabet),
                    textButtonNext = stringResource(id = R.string.repeat),
                    isVisibleSecondButton = true,
                    onDismissRequest = {},
                )
            }
        }
    }
}

private fun String.appendChar(char: String): String {
    return char + this
}