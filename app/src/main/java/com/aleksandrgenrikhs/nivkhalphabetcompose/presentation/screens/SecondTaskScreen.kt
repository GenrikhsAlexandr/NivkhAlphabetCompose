package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.NotConnected
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.SecondTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.SecondTaskViewModel
import kotlinx.coroutines.delay

@Composable
fun SecondTaskScreen(
    navController: NavController,
    viewModel: SecondTaskViewModel = hiltViewModel(),
    letter: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.setLetter(letter)

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getWords(letter)
    })

    with(uiState) {
        if (!isNetworkConnected) {
            NotConnected(
                navController = navController
            )
        }

        if (words.isNotEmpty()) {
            SecondTaskLayout(
                words = words,
                letter = letter,
                onClick = { wordId, letterId -> viewModel.flipCard(wordId, letterId) },
                isClickable = !isAnswerCorrect
            )
            if (isCompleted) {
                val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task2)
                var showDialog by remember { mutableStateOf(false) }
                LaunchedEffect(key1 = null) {
                    delay(2000)
                    showDialog = true
                }
                if (showDialog) {
                    Dialog(
                        navigationBack = {
                            navController.popBackStack(
                                NavigationDestination.LettersScreen.destination,
                                inclusive = false
                            )
                        },
                        navigationNext = {
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
}