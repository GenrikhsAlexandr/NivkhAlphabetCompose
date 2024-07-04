package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    currentScreen: String,
    navController: NavHostController?,
    letter: String?
) {
    CenterAlignedTopAppBar(
        title = {
            when (currentScreen) {
                NavigationDestination.LettersScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                NavigationDestination.TasksScreen.destination -> {
                    Text(
                        text = letter!!,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                NavigationDestination.FirstTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.firstTask),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                NavigationDestination.SecondTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.secondTask),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                NavigationDestination.ThirdTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.thirdTask),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                NavigationDestination.FourthTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.fourthTask),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        navigationIcon = {
            if (currentScreen != NavigationDestination.LettersScreen.destination
                && currentScreen != NavigationDestination.StartScreen.destination
            ) {
                IconButton(onClick = {
                    navController?.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = colorText,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        modifier = modifier
            .background(colorPrimary),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            contentColorFor(colorPrimary)
        )
    )
}