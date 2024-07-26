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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    letter: String?,
    scrollBehavior: TopAppBarScrollBehavior?
) {
    CenterAlignedTopAppBar(
        title = {
            when (currentScreen) {
                NavigationDestination.LettersScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
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
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                NavigationDestination.SecondTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.secondTask),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                NavigationDestination.ThirdTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.thirdTask),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                NavigationDestination.FourthTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.fourthTask),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                NavigationDestination.AboutScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        navigationIcon = {
            if (currentScreen != NavigationDestination.LettersScreen.destination
                && currentScreen != NavigationDestination.SplashScreen.destination
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
        actions = {
            when (currentScreen) {
                NavigationDestination.LettersScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoLettersScreen
                    ),
                )

                NavigationDestination.TasksScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoTasksScreen, letter!!
                    ),
                )

                NavigationDestination.FirstTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoFirstScreen, letter!!
                    ),
                )

                NavigationDestination.SecondTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoSecondScreen, letter!!
                    ),
                )

                NavigationDestination.ThirdTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoThirdScreen
                    ),
                )

                NavigationDestination.FourthTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoFourthScreen
                    ),
                )

                NavigationDestination.RepeatTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoRepeatScreen
                    ),
                )
            }
        },
        modifier = modifier
            .background(colorPrimary),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorPrimary,
            scrolledContainerColor = (colorPrimary)
        ),
        scrollBehavior = scrollBehavior,
    )
}