package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskWriteWordLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.TaskWriteWordViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO

@Composable
fun TaskWriteWordScreen(
    navController: NavController,
    viewModel: TaskWriteWordViewModel = hiltViewModel(),
    letter: String,
) {
    val viewState by viewModel.uiState.collectAsState()
    viewModel.setSelectedLetter(letter)

    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWordsForLetter(letter)
            isLaunchedEffectCalled = true
        }
    }

    TaskWriteWordLayout(
        viewState = viewState,
        onClick = { viewModel.playSound("${WORDS_AUDIO}${viewState.wordId}") },
        onDone = (viewModel::checkUserGuess),
        onInputChange = { viewModel.onInputChange(it) },
        onDelete = { viewModel.deleteLastLetter() },
        onBack = navController::popBackStack
    )
    if (viewState.showDialog) {
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
            painter = painterResource(id = R.drawable.ic_end_task4),
            title = stringResource(id = R.string.textEndFourthTask),
            textButtonBack = stringResource(id = R.string.backAlphabet),
            textButtonNext = stringResource(id = R.string.repeat),
            isVisibleSecondButton = true,
        )
    }
}
