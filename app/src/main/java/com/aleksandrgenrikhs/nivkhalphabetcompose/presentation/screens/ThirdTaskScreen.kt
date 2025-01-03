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
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.ThirdTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.ThirdTaskViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO

@Composable
fun ThirdTaskScreen(
    navController: NavController,
    viewModel: ThirdTaskViewModel = hiltViewModel(),
    letter: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    viewModel.setSelectedLetter(letter)

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWordsForLetter(letter)
            isLaunchedEffectCalled = true
        }
    }

    with(uiState) {
        ThirdTaskLayout(
            wordsId = wordsId,
            icons = icons,
            currentWords = currentWords,
            shareWords = shareableWords,
            isGuessWrong = isGuessWrong,
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
        if (isAnswerCorrect) {
            showDialog = true
        }
        if (showDialog) {
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
                        "${NavigationDestination.FourthTaskScreen.destination}/$letter"
                    ) {
                        popUpTo("${NavigationDestination.ThirdTaskScreen.destination}/$letter") {
                            inclusive = true
                        }
                    }
                },
                painter = painter,
                title = stringResource(id = R.string.textEndThirdTask),
                textButtonBack = stringResource(id = R.string.backAlphabet),
                textButtonNext = stringResource(
                    id = R.string.nextTask,
                    Task.FOURTH.stableId
                ),
                isVisibleSecondButton = true,
                onDismissRequest = {}
            )
        }
    }
}