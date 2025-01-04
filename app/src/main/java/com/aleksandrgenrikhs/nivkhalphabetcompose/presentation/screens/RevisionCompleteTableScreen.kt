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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.RevisionCompleteTableLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.RevisionCompleteTableViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO

@Composable
fun RevisionCompleteTableScreen(
    navController: NavController,
    viewModel: RevisionCompleteTableViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWords()
            isLaunchedEffectCalled = true
        }
    }

    with(uiState) {
        RevisionCompleteTableLayout(
            title = title,
            letter = letter,
            icon = icon,
            currentWords = currentWords,
            currentLetters = currentLetters,
            currentIcons = currentIcons,
            shareWords = shareWords,
            shareLetters = shareLetters,
            shareIcons = shareIcons,
            isGuessWrong = isGuessWrong,
            onDone = (viewModel::checkAnswer),
            onReset = (viewModel::resetState),
            onBack = navController::popBackStack
        ) { transferData: DragAndDropEvent, index: Int ->
            viewModel.updateReceivingContainer(
                transferData.toAndroidDragEvent().clipData,
                index
            )
        }
        if (isAnswerCorrect) {
            if (shouldPlayFinishAudio) {
                viewModel.playSound(FINISH_AUDIO)
            }
            val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task3)
            DialogSuccess(
                navigationBack = {
                    navController.popBackStack(
                        NavigationDestination.LettersScreen.destination,
                        inclusive = false
                    )
                },
                navigationNext = {
                    navController.navigate(
                        NavigationDestination.RevisionTasksScreen.destination,
                    ) {
                        popUpTo(NavigationDestination.RevisionTasksScreen.destination) {
                            inclusive = true
                        }
                    }
                },
                painter = painter,
                title = stringResource(id = R.string.textEndRevisionThird),
                textButtonBack = stringResource(id = R.string.backAlphabet),
                textButtonNext = stringResource(id = R.string.repeat),
                isVisibleSecondButton = true,
            )
        }
    }
}
