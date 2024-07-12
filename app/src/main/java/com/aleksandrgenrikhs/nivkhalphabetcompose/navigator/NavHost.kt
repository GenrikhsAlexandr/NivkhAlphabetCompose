package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.AppBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.FirstTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.FourthTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.LetterScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SecondTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.StartScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.ThirdTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    val currentBackStackEntry by navController!!.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route?.substringBefore("/")
    val letter = currentBackStackEntry?.arguments?.getString(Constants.LETTER_KEY)

    Scaffold(
        topBar = {
            currentDestination?.let {
                AppBar(
                    modifier = modifier
                        .padding(top = 8.dp),
                    currentScreen = it,
                    letter = letter,
                    navController = navController,
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = checkNotNull(navController),
            startDestination = NavigationDestination.StartScreen.destination
        ) {
            composable(
                route = NavigationDestination.StartScreen.destination,
            ) {
                StartScreen(
                    navController = navController,
                )
            }
            composable(
                route = NavigationDestination.LettersScreen.destination,
            ) {
                LetterScreen(
                    navController = navController,
                )
            }
            composable(
                route = "${NavigationDestination.TasksScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(navArgument(Constants.LETTER_KEY) { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(Constants.LETTER_KEY)?.let {
                    TasksScreen(
                        modifier = Modifier,
                        navController = navController,
                        letter = it,
                    )
                }
            }
            composable(
                route = "${NavigationDestination.FirstTaskScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(navArgument(Constants.LETTER_KEY) { type = NavType.StringType }
                )
            )
            { backStackEntry ->
                val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
                FirstTaskScreen(
                    letter = checkNotNull(currentLetter),
                    navController = navController,
                )
            }
            composable(
                route = "${NavigationDestination.SecondTaskScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(
                    navArgument(Constants.LETTER_KEY) { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
                SecondTaskScreen(
                    letter = checkNotNull(currentLetter),
                    navController = navController,
                )
            }
            composable(
                route = "${NavigationDestination.ThirdTaskScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(
                    navArgument(Constants.LETTER_KEY) { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
                ThirdTaskScreen(
                    letter = checkNotNull(currentLetter),
                    navController = navController,
                )
            }
            composable(
                route = "${NavigationDestination.FourthTaskScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(
                    navArgument(Constants.LETTER_KEY) { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
                FourthTaskScreen(
                    letter = checkNotNull(currentLetter),
                    navController = navController,
                )
            }
        }
    }
}