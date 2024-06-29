package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight

@Composable
fun DialogLetterGameOver(
    letter: String,
    navController: NavController,
) {
    AlertDialog(
        onDismissRequest = {
            navController.popBackStack(
                NavigationDestination.LettersScreen.destination,
                inclusive = false
            )
        },
        title = {
            Text(
                stringResource(R.string.studied, letter),
                color = colorRight,
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    navController.popBackStack(
                        NavigationDestination.LettersScreen.destination,
                        inclusive = false
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.mainScreen),
                    color = colorRight,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    navController.popBackStack(
                        "${NavigationDestination.TasksScreen.destination}/$letter",
                        inclusive = false
                    )
                }) {
                Text(
                    text = stringResource(R.string.repeat),
                    color = colorRight,
                )
            }
        }
    )
}