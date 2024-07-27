package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.RevisionTask
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.RevisionTaskLayout

@Composable
fun RevisionTasksScreen(
    navController: NavController,
) {
    RevisionTaskLayout(
        titleResId = RevisionTask.entries.map { it.titleResId },
        iconId = RevisionTask.entries.map { it.icon },
        route = RevisionTask.entries.map { it.route },
        onClick = { route ->
            navController.navigate(route)
        }
    )
}