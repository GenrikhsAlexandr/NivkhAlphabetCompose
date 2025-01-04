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
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskFindWordLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.TaskFindWordViewModel

@Composable
fun TaskFindWordScreen(
    navController: NavController,
    viewModel: TaskFindWordViewModel = hiltViewModel(),
    letter: String,
) {
    val viewState by viewModel.uiState.collectAsState()
    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    viewModel.setSelectedLetter(letter)

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWordsForLetter(letter)
            isLaunchedEffectCalled = true
        }
    }

    TaskFindWordLayout(
        viewState = viewState,
        letter = letter,
        onClick = (viewModel::flipCard),
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
                    "${NavigationDestination.TaskMatchWordsScreen.destination}/$letter"
                ) {
                    popUpTo("${NavigationDestination.TaskFindWordScreen.destination}/$letter") {
                        inclusive = true
                    }
                }
            },
            painter = painterResource(id = R.drawable.ic_end_task2),
            title = stringResource(id = R.string.textEndSecondTask),
            textButtonBack = stringResource(id = R.string.backAlphabet),
            textButtonNext = stringResource(
                id = R.string.nextTask,
                Task.MATCH_WORDS.stableId
            ),
            isVisibleSecondButton = true,
            onDismissRequest = {}
        )
    }
}
