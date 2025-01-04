package com.aleksandrgenrikhs.nivkhalphabetcompose.navigator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.AboutScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.CertificateScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.LetterScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionChooseRightWordScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionCompleteTableScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionListenAndChooseScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.RevisionTasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.SplashScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TaskFindWordScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TaskLearnLetterScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TaskMatchWordsScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TaskWriteWordScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.TasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants

private fun AnimatedContentTransitionScope<*>.enterAnimation() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(500)
)

fun AnimatedContentTransitionScope<*>.exitAnimation() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(500)
)

fun AnimatedContentTransitionScope<*>.popEnterTransition() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(500)
)

fun AnimatedContentTransitionScope<*>.popExitTransition() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(500)
)

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.SplashScreen.destination,
        enterTransition = { enterAnimation() },
        exitTransition = { exitAnimation() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() },
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
            route = "${NavigationDestination.TaskLearnLetterScreen.destination}/{${Constants.LETTER_KEY}}",
            arguments = listOf(navArgument(Constants.LETTER_KEY) { type = NavType.StringType })
        )
        { backStackEntry ->
            val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
            TaskLearnLetterScreen(
                letter = checkNotNull(currentLetter),
                navController = navController,
            )
        }
        composable(
            route = "${NavigationDestination.TaskFindWordScreen.destination}/{${Constants.LETTER_KEY}}",
            arguments = listOf(
                navArgument(Constants.LETTER_KEY) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
            TaskFindWordScreen(
                letter = checkNotNull(currentLetter),
                navController = navController,
            )
        }
        composable(
            route = "${NavigationDestination.TaskMatchWordsScreen.destination}/{${Constants.LETTER_KEY}}",
            arguments = listOf(
                navArgument(Constants.LETTER_KEY) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
            TaskMatchWordsScreen(
                letter = checkNotNull(currentLetter),
                navController = navController,
            )
        }
        composable(
            route = "${NavigationDestination.TaskWriteWordScreen.destination}/{${Constants.LETTER_KEY}}",
            arguments = listOf(
                navArgument(Constants.LETTER_KEY) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val currentLetter = backStackEntry.arguments?.getString(Constants.LETTER_KEY)
            TaskWriteWordScreen(
                letter = checkNotNull(currentLetter),
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.RevisionTasksScreen.destination,
        ) {
            RevisionTasksScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.RevisionListenAndChooseScreen.destination,
        ) {
            RevisionListenAndChooseScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.RevisionChooseRightWordScreen.destination,
        ) {
            RevisionChooseRightWordScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.RevisionCompleteTableScreen.destination,
        ) {
            RevisionCompleteTableScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.AboutScreen.destination
        )
        {
            AboutScreen(navController = navController)
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
                    navController = navController,
                )
            }
        }
    }
}
