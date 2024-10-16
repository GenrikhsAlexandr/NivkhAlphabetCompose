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
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.SecondTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.SecondTaskViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.SettingViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import kotlinx.coroutines.delay

@Composable
fun SecondTaskScreen(
    navController: NavController,
    viewModel: SecondTaskViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    letter: String,
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val settingUiState by settingViewModel.uiState.collectAsState()
    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    viewModel.setSelectedLetter(letter)

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWordsForLetter(letter)
            isLaunchedEffectCalled = true
        }
    }

    with(uiState) {
        SecondTaskLayout(
            lettersId = lettersId,
            titles = titles,
            wordsId = wordsId,
            icons = icons,
            isFlipped = isFlipped,
            isCorrectAnswer = isCorrectAnswers,
            letter = letter,
            onClick = (viewModel::flipCard),
            isClickable = !isCorrectWord,
            onDividerVisibilityChange = onDividerVisibilityChange
        )
        if (isCompleted) {
            val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task2)
            var showDialog by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = null) {
                delay(2000)
                showDialog = true
            }
            if (showDialog) {
                if (!isFinishAudio && settingUiState.isSoundEnable) {
                    viewModel.playSound(FINISH_AUDIO)
                }
                DialogSuccess(
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
                            "${NavigationDestination.ThirdTaskScreen.destination}/$letter"
                        ) {
                            popUpTo("${NavigationDestination.SecondTaskScreen.destination}/$letter") {
                                inclusive = true
                            }
                        }
                    },
                    painter = painter,
                    title = stringResource(id = R.string.textEndSecondTask),
                    textButtonBack = stringResource(id = R.string.backAlphabet),
                    textButtonNext = stringResource(
                        id = R.string.nextTask,
                        Task.THIRD.stableId
                    ),
                    isVisibleSecondButton = true,
                    onDismissRequest = {}
                )
            }
        }
    }
}