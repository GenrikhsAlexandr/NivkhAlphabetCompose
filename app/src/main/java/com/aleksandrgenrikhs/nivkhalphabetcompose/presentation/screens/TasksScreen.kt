package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.NotConnected
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.TasksViewModel

@Composable
fun TasksScreen(
    modifier: Modifier,
    navController: NavController,
    letter: String,
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val uiState by tasksViewModel.uiState.collectAsState()
    with(uiState) {
        LaunchedEffect(key1 = letter, block = {
            tasksViewModel.isTaskCompleted(letter)
        }
        )

        if (!isNetworkConnected) {
            NotConnected(
                navController = navController
            )
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            items(task) {
                TaskItem(
                    task = it.task.titleResId,
                    iconResId = it.task.icon,
                    onTaskClick = {
                        if (it.isNextTaskVisible) {
                            navController.navigate("${it.task.route}/$letter")
                        }
                    },
                    isClickable = it.isNextTaskVisible
                )
            }
        }
    }
}