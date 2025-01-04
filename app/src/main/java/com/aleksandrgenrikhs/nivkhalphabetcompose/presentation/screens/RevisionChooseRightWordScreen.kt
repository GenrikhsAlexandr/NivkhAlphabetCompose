package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DialogSuccess
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.RevisionChooseRightWordLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.RevisionChooseRightWordViewModel

@Composable
fun RevisionChooseRightWordScreen(
    navController: NavController,
    viewModel: RevisionChooseRightWordViewModel = hiltViewModel(),
) {
    val viewState by viewModel.uiState.collectAsState()
    var isLaunchedEffectCalled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isLaunchedEffectCalled) {
            viewModel.updateWords()
            isLaunchedEffectCalled = true
        }
    }

    RevisionChooseRightWordLayout(
        viewState = viewState,
        onWordClick = (viewModel::checkUserGuess),
        onIconClick = (viewModel::playSound),
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
                    NavigationDestination.RevisionCompleteTableScreen.destination
                ) {
                    popUpTo(NavigationDestination.RevisionChooseRightWordScreen.destination) {
                        inclusive = true
                    }
                }
            },
            painter = painterResource(R.drawable.ic_end_revision2),
            title = stringResource(id = R.string.textEndRevisionSecond),
            textButtonBack = stringResource(id = R.string.backAlphabet),
            textButtonNext = stringResource(id = R.string.nextRevisionTasks),
            isVisibleSecondButton = true,
            onDismissRequest = {}
        )
    }
}
