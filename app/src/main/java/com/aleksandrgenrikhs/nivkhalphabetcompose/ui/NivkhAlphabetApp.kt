package com.aleksandrgenrikhs.nivkhalphabetcompose.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.firsttask.FirstTaskScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.letters.LetterScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.listtasks.TasksScreen
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTER_KEY
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.TASK_KEY

@Composable
fun NivkhAlphabetApp(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    val currentBackStackEntry by navController!!.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route?.substringBefore("/")
    val letter = currentBackStackEntry?.arguments?.getString(LETTER_KEY)

    Scaffold(
        topBar = {
            currentDestination?.let {
                TopAppBarAlphabet(
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
            startDestination = NavigationDestination.LettersScreen.destination
        ) {
            composable(
                route = NavigationDestination.LettersScreen.destination,
            ) {
                LetterScreen(
                    modifier = modifier,
                    navController = navController,
                )
            }
            composable(
                route = "${NavigationDestination.TasksScreen.destination}/{$LETTER_KEY}",
                arguments = listOf(navArgument(LETTER_KEY) { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(LETTER_KEY)?.let {
                    TasksScreen(
                        modifier = Modifier,
                        navController = navController,
                        letter = it,
                    )
                }
            }
            composable(
                route = "${NavigationDestination.FirstTaskScreen.destination}/{$LETTER_KEY}",
                arguments = listOf(navArgument(LETTER_KEY) { type = NavType.StringType }
                )
            )
            { backStackEntry ->
                val currentLetter = backStackEntry.arguments?.getString(LETTER_KEY)
                FirstTaskScreen(
                    letter = checkNotNull(currentLetter),
                    navController = navController,
                )
            }
            composable(
                route = "${NavigationDestination.SecondTaskScreen.destination}/{$LETTER_KEY}",
                arguments = listOf(navArgument(LETTER_KEY) { type = NavType.StringType },
                    navArgument(TASK_KEY) { type = NavType.IntType }),
            ) {
                TODO()

            }
            composable(
                route = "${NavigationDestination.ThirdTaskScreen.destination}/{$LETTER_KEY}",
                arguments = listOf(navArgument(LETTER_KEY) { type = NavType.StringType },
                    navArgument(TASK_KEY) { type = NavType.IntType }),
            ) {
                TODO()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarAlphabet(
    modifier: Modifier = Modifier,
    currentScreen: String,
    navController: NavHostController?,
    letter: String?
) {
    val context = LocalContext.current
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
                        style = MaterialTheme.typography.displayLarge,
                    )
                }

                NavigationDestination.FirstTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.titleFistTask),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                NavigationDestination.SecondTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.titleSecondTask),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                NavigationDestination.ThirdTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.titleThirdTask),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        navigationIcon = {
            if (currentScreen != NavigationDestination.LettersScreen.destination) {
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
            if (currentScreen == NavigationDestination.LettersScreen.destination) {
                IconButton(onClick = {
                    Toast.makeText(context, "Ничего нет", Toast.LENGTH_LONG).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Star,
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