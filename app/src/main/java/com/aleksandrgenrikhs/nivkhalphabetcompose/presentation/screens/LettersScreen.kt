package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.LettersLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.LettersViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.REVISION_TASKS_SCREEN
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASKS_SCREEN


@Composable
fun LetterScreen(
    navController: NavController,
    viewModel: LettersViewModel = hiltViewModel(),
    onDividerVisibilityChange: (Boolean) -> Unit,
    isLettersCompleted: (Boolean) -> Unit,
    isCertificateCreated: (Boolean) -> Unit,
    timeLearningAlphabet: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {
        LaunchedEffect(Unit) {
            viewModel.updateLetters()
            viewModel.checkLetterCompleted()
            viewModel.checkCertificateStatus()
            viewModel.checkAllLettersCompleted()
            viewModel.getTimeLearning()
        }
        isLettersCompleted(isAllLettersCompleted)
        isCertificateCreated(getCertificateStatus)
        timeLearningAlphabet(timeLearning)

        LettersLayout(
            letters = letters,
            isLetterCompleted = isLetterCompleted,
            onClickLetter = { letter ->
                navController.navigate("${TASKS_SCREEN}/$letter")
                onDividerVisibilityChange(false)
            },
            onClickRevision = {
                navController.navigate(REVISION_TASKS_SCREEN)
                onDividerVisibilityChange(false)
            },
            onDividerVisibilityChange = onDividerVisibilityChange
        )
    }
}