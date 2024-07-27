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
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val uiState by tasksViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = letter, block = { tasksViewModel.isTaskCompleted(letter) })

    with(uiState) {
        TaskLayout(
            titleResId = titleResId,
            iconId = iconId,
            isTaskCompleted = isTaskCompleted,
            isTaskVisible = isNextTaskVisible,
            route = route,
            letter = letter,
            onClick = { route, letter ->
                navController.navigate("$route/$letter")
            }
        )
    }
}