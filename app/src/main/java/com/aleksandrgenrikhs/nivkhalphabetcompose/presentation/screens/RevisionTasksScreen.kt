package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.RevisionTask
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.TaskRevisionLayout

@Composable
fun RevisionTasksScreen(
    navController: NavController,
) {
    TaskRevisionLayout(
        titles = RevisionTask.entries.map { it.titleResId },
        icons = RevisionTask.entries.map { it.icon },
        routes = RevisionTask.entries.map { it.route },
        onClick = { route ->
            navController.navigate(route)
        },
        onBack = navController::popBackStack
    )
}