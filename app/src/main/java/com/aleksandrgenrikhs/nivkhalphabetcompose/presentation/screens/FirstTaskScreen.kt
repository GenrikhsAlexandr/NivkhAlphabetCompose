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
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.FirstTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.FirstTaskViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO

@Composable
fun FirstTaskScreen(
    navController: NavController,
    viewModel: FirstTaskViewModel = hiltViewModel(),
    letter: String,
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by rememberSaveable { mutableStateOf(false) }

    viewModel.setLetter(letter)

    with(uiState) {
        LaunchedEffect(Unit) {
            viewModel.getWords(letter)
            viewModel.isTaskCompleted(letter)
        }
        FirstTaskLayout(
            titles = titles,
            wordsId = wordsId,
            icons = icons,
            progressWords = progressWords,
            isClickableWords = isClickableWords,
            onClick = (viewModel::onClickElement),
            letter = letter,
            isClickableLetter = isClickableLetter,
            isPlaying = isPlaying,
            isVisibleWord = isVisibleWord,
            progressLetter = progressLetter,
            onDividerVisibilityChange = onDividerVisibilityChange
        )

        if (isCompletedWords.isNotEmpty() && isCompletedWords.last() && !isPlaying) {
            showDialog = true
        }
        if (showDialog) {
            if (!isFinishAudio) {
                viewModel.playSound(FINISH_AUDIO)
            }
            val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task1)
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
                        "${NavigationDestination.SecondTaskScreen.destination}/$letter"
                    ) {
                        popUpTo("${NavigationDestination.FirstTaskScreen.destination}/$letter") {
                            inclusive = true
                        }
                    }
                },
                painter = painter,
                title = stringResource(id = R.string.textEndFirstTask),
                textButtonBack = stringResource(id = R.string.backAlphabet),
                textButtonNext = stringResource(
                    id = R.string.nextTask,
                    Task.SECOND.stableId
                ),
                isVisibleSecondButton = true,
                onDismissRequest = {}
            )
        }
    }
}