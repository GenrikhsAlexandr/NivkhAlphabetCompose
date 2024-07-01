package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.FourthTaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.NotConnected
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.FourthTaskViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import kotlinx.coroutines.delay

@Composable
fun FourthTaskScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FourthTaskViewModel = hiltViewModel(),
    letter: String,
) {
    viewModel.setLetter(letter)
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getWord(letter)
    })

    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.isNetworkConnected) {
        NotConnected(
            navController = navController
        )
    }

    if (uiState.title.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary)
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {


            FourthTaskLayout(
                onClick = { viewModel.playSound("${WORDS_AUDIO}${uiState.wordId}") },
                icon = uiState.icon,
                onDone = { viewModel.checkUserGuess(uiState.userGuess) },
                isGuessWrong = uiState.isGuessWrong,
                onUserGuessChanged = { viewModel.updateUserGuess(it.appendChar(uiState.userGuess)) },
                userGuess = uiState.userGuess,
                onDelete = { viewModel.deleteLastLetter() }
            )
            if (uiState.isCompleted) {
                val painter = rememberAsyncImagePainter(model = R.drawable.ic_end_task5)
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
                                "${NavigationDestination.TasksScreen.destination}/$letter"
                            ) {
                                popUpTo("${NavigationDestination.FourthTaskScreen.destination}/$letter") {
                                    inclusive = true
                                }
                            }
                        },
                        painter = painter,
                        title = stringResource(id = R.string.textEndFifthTask),
                        textButtonBack = stringResource(id = R.string.backAlphabet),
                        textButtonNext = stringResource(id = R.string.repeat),
                        isVisibleSecondButton = true,
                        onDismissRequest = {}
                    )
                }
            }
        }
    }
}

private fun String.appendChar(char: String): String {
    return char + this
}