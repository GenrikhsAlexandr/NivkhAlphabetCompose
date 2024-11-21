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
    navController: NavHostController
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
            arguments = listOf(navArgument(Constants.LETTER_KEY) {
                type = NavType.StringType
            })
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
            FirstRevisionScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.RevisionSecondScreen.destination,
        ) {
            SecondRevisionScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.RevisionThirdScreen.destination,
        ) {
            ThirdRevisionScreen(
                navController = navController,
            )
        }
        composable(
            route = NavigationDestination.AboutScreen.destination
        )
        {
            AboutScreen(
                navController = navController,
            )
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