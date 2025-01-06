package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskLearnLetterLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.TaskLearnLetterViewModel

@Composable
fun TaskLearnLetterScreen(
    navController: NavController,
    viewModel: TaskLearnLetterViewModel = hiltViewModel(),
    letter: String,
) {
    val viewState by viewModel.uiState.collectAsState()

    viewModel.setSelectedLetter(letter)

    LaunchedEffect(Unit) {
        viewModel.updateWordsForLetter(letter)
        viewModel.checkTaskCompletion(letter)
    }
    TaskLearnLetterLayout(
        onClickLetter = (viewModel::onClickLetter),
        onClickWord = (viewModel::onClickWord),
        letter = letter,
        viewState = viewState,
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
                    "${NavigationDestination.TaskFindWordScreen.destination}/$letter"
                ) {
                    popUpTo("${NavigationDestination.TaskLearnLetterScreen.destination}/$letter") {
                        inclusive = true
                    }
                }
            },
            painter = painterResource(R.drawable.ic_end_task1),
            title = stringResource(id = R.string.textEndFirstTask),
            textButtonBack = stringResource(id = R.string.backAlphabet),
            textButtonNext = stringResource(
                id = R.string.nextTask,
                Task.FIND_WORD.stableId
            ),
            isVisibleSecondButton = true,
        )
    }
}
