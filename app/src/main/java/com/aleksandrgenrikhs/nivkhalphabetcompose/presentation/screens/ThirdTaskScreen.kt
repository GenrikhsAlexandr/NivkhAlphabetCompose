package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
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

    viewModel.setLetter(letter)

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getWords(letter)
    })

    with(uiState) {
            ThirdTaskLayout(
                title = title,
                wordId = wordId,
                icon = icon,
                currentWords = currentWords,
                shareWords = shareWords,
                isGuessWrong = isGuessWrong,
                onIconClick = (viewModel::playSound),
                onDone = (viewModel::checkAnswer),
            ) { transferData: DragAndDropEvent, index: Int ->
                viewModel.updateReceivingContainer(
                    transferData.toAndroidDragEvent().clipData,
                    index
                )
            }
            if (isAnswerCorrect) {
                if (!isFinishAudio) {
                    viewModel.playSound(FINISH_AUDIO)
                }
                val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task3)
                Dialog(
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