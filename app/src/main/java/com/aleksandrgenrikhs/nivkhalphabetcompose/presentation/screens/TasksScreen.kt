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
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    val uiState by tasksViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { tasksViewModel.isTaskCompleted(letter) }

    with(uiState) {
        TaskLayout(
            titles = titles,
            icons = icons,
            isTaskCompleted = isTaskCompleted,
            isTaskVisible = isNextTaskVisible,
            routes = routes,
            letter = letter,
            onClick = { route, letter ->
                navController.navigate("$route/$letter")
                onDividerVisibilityChange(false)
            },
            onDividerVisibilityChange = onDividerVisibilityChange
        )
    }
}