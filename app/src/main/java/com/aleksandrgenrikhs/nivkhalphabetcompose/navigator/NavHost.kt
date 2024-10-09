package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.AppBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.AboutScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.CertificateScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.FirstRevisionScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.FirstTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.FourthTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.LetterScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionTasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SecondRevisionScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SecondTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SplashScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.ThirdRevisionScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.ThirdTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route?.substringBefore("/")
    val letter = currentBackStackEntry?.arguments?.getString(Constants.LETTER_KEY)
    var isDividerVisible by remember { mutableStateOf(false) }
    var isLetterCompleted by remember { mutableStateOf(false) }
    var isNameNotEmpty by remember { mutableStateOf(false) }
    var nameUser by remember { mutableStateOf("") }
    var timeLearningAlphabet by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            if (currentDestination != NavigationDestination.SplashScreen.destination) {
                currentDestination?.let {
                    Column {
                        AppBar(
                            currentScreen = it,
                            letter = letter,
                            navController = navController,
                            isLettersCompleted = isLetterCompleted,
                            onDividerVisibilityChange = { isVisibility ->
                                isDividerVisible = isVisibility
                            },
                            isNameNotEmpty = isNameNotEmpty,
                            name = nameUser,
                            timeLearning = timeLearningAlphabet,
                        )
                        if (isDividerVisible) {
                            HorizontalDivider(
                                color = colorText
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier
                .padding(innerPadding),
            navController = checkNotNull(navController),
            startDestination = NavigationDestination.SplashScreen.destination,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            composable(
                route = NavigationDestination.SplashScreen.destination,
            ) {
                SplashScreen(
                    navController = navController,
                )
            }
            composable(
                route = NavigationDestination.LettersScreen.destination,
            ) {
                LetterScreen(
                    navController = navController,
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    },
                    isLettersCompleted = { isCompleted ->
                        isLetterCompleted = isCompleted
                    },
                    isNameNotEmpty = { isNotEmpty ->
                        isNameNotEmpty = isNotEmpty
                    },
                    name = { name ->
                        nameUser = name
                    },
                    timeLearningAlphabet = { time ->
                        timeLearningAlphabet = time
                    }
                )
            }
            composable(
                route = "${NavigationDestination.TasksScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(navArgument(Constants.LETTER_KEY) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(Constants.LETTER_KEY)?.let {
                    TasksScreen(
                        navController = navController,
                        letter = it,
                        onDividerVisibilityChange = { isVisibility ->
                            isDividerVisible = isVisibility
                        }
                    )
                }
            }
            composable(
                route = "${NavigationDestination.FirstTaskScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(navArgument(Constants.LETTER_KEY) {
                    type = NavType.StringType
                }
                )
            )
            { backStackEntry ->
                val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
                FirstTaskScreen(
                    letter = checkNotNull(currentLetter),
                    navController = navController,
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
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
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
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
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
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
            composable(
                route = NavigationDestination.RevisionTaskScreen.destination,
            ) {
                RevisionTasksScreen(
                    navController = navController,
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
                )
            }
            composable(
                route = NavigationDestination.RevisionFirstScreen.destination,
            ) {
                FirstRevisionScreen(
                    navController = navController,
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
                )
            }
            composable(
                route = NavigationDestination.RevisionSecondScreen.destination,
            ) {
                SecondRevisionScreen(
                    navController = navController,
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
                )
            }
            composable(
                route = NavigationDestination.RevisionThirdScreen.destination,
            ) {
                ThirdRevisionScreen(
                    navController = navController,
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    }
                )
            }
            composable(
                route = NavigationDestination.AboutScreen.destination
            )
            {
                AboutScreen(
                    onDividerVisibilityChange = { isVisibility ->
                        isDividerVisible = isVisibility
                    })
            }
            composable(
                route = "${NavigationDestination.CertificateScreen.destination}/{${Constants.NAME_KEY}}",
                arguments = listOf(navArgument(Constants.NAME_KEY) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(Constants.NAME_KEY)?.let {
                    CertificateScreen(
                        name = it,
                    )
                }
            }
        }
    }
}