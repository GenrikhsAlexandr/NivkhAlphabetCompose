package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskLayout
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.TasksViewModel

@Composable
fun TasksScreen(
    navController: NavController,
    letter: String,
    tasksViewModel: TasksViewModel = hiltViewModel(),
) {
    val viewState by tasksViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { tasksViewModel.checkTaskCompletion(letter) }

    TaskLayout(
        viewSate = viewState,
        letter = letter,
        onClick = { route ->
            navController.navigate("$route/$letter")
        },
        onBack = navController::popBackStack
    )
}
