package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.thirdtask

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.ThirdTaskViewModel

@Composable
fun ThirdTaskScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ThirdTaskViewModel = hiltViewModel(),
    letter: String,
) {
    viewModel.setLetter(letter)
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getShuffledWord(letter)
    })

    val uiState by viewModel.uiState.collectAsState()
    if (uiState.shuffledWord.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary)
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            TextThirdTask(
                word = uiState.shuffledWord
            )
            ButtonThirdTask(
                onClick = { },
                icon = uiState.icon
            )
        }
        if (uiState.isCompleted) {
            navController.popBackStack(
                "${NavigationDestination.TasksScreen.destination}/$letter",
                inclusive = false
            )
        }
    }
}