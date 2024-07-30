package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionFirstScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionSecondScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionTasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionThirdScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SecondTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SplashScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.ThirdTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val isScrolled = remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0f } }
    val currentBackStackEntry by navController!!.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route?.substringBefore("/")
    val letter = currentBackStackEntry?.arguments?.getString(Constants.LETTER_KEY)
    LaunchedEffect(currentBackStackEntry) {
        topAppBarState.heightOffset = 0f
    }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentDestination != NavigationDestination.SplashScreen.destination) {
                currentDestination?.let {
                    Column {
                        AppBar(
                            currentScreen = it,
                            letter = letter,
                            navController = navController,
                            scrollBehavior = scrollBehavior
                        )
                        if (isScrolled.value) {
                            HorizontalDivider(
                                modifier
                                    .fillMaxWidth(),
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
                )
            }
            composable(
                route = "${NavigationDestination.TasksScreen.destination}/{${Constants.LETTER_KEY}}",
                arguments = listOf(navArgument(Constants.LETTER_KEY) { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(Constants.LETTER_KEY)?.let {
                    TasksScreen(
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
            composable(
                route = NavigationDestination.RevisionTaskScreen.destination,
            ) {
                RevisionTasksScreen(
                    navController = navController,
                )
            }
            composable(
                route = NavigationDestination.RevisionFirstScreen.destination,
            ) {
                RevisionFirstScreen(
                    navController = navController,
                )
            }
            composable(
                route = NavigationDestination.RevisionSecondScreen.destination,
            ) {
                RevisionSecondScreen(
                    navController = navController,
                )
            }
            composable(
                route = NavigationDestination.RevisionThirdScreen.destination,
            ) {
                RevisionThirdScreen(
                    navController = navController,
                )
            }
        }
    }
}