package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskMatchWordsLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.TaskMatchWordsViewModel

@Composable
fun TaskMatchWordsScreen(
    navController: NavController,
    viewModel: TaskMatchWordsViewModel = hiltViewModel(),
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

    TaskMatchWordsLayout(
        viewState = viewState,
        onIconClick = (viewModel::playSound),
        onDone = (viewModel::checkAnswer),
        onReset = (viewModel::resetState),
        onBack = navController::popBackStack
    ) { transferData: DragAndDropEvent, index: Int ->
        viewModel.updateReceivingContainer(
            transferData.toAndroidDragEvent().clipData,
            index
        )
    }

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
                    "${NavigationDestination.TaskWriteWordScreen.destination}/$letter"
                ) {
                    popUpTo("${NavigationDestination.TaskMatchWordsScreen.destination}/$letter") {
                        inclusive = true
                    }
                }
            },
            painter = painterResource(R.drawable.ic_end_task3),
            title = stringResource(id = R.string.textEndThirdTask),
            textButtonBack = stringResource(id = R.string.backAlphabet),
            textButtonNext = stringResource(
                id = R.string.nextTask,
                Task.WRITE_WORD.stableId
            ),
            isVisibleSecondButton = true,
        )
    }
}
