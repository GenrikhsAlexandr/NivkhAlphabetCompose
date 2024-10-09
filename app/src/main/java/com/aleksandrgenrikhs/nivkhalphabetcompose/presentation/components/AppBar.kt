package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.idapgroup.autosizetext.AutoSizeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    currentScreen: String,
    navController: NavHostController,
    letter: String?,
    isLettersCompleted: Boolean,
    isNameNotEmpty: Boolean,
    name: String,
    timeLearning: Int,
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .wrapContentHeight()
            .background(colorPrimary),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorPrimary,
        ),
        title = {
            when (currentScreen) {
                NavigationDestination.LettersScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.appName),
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
                    AutoSizeText(
                        text = stringResource(id = R.string.fourthTask),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        minFontSize = 16.sp,
                    )
                }

                NavigationDestination.RevisionTaskScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.revisionTask),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                NavigationDestination.RevisionFirstScreen.destination -> {
                    AutoSizeText(
                        text = stringResource(id = R.string.revisionFirst),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        minFontSize = 20.sp,
                    )
                }

                NavigationDestination.RevisionSecondScreen.destination -> {
                    AutoSizeText(
                        text = stringResource(id = R.string.revisionSecond),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        minFontSize = 20.sp,
                    )
                }

                NavigationDestination.RevisionThirdScreen.destination -> {
                    AutoSizeText(
                        text = stringResource(id = R.string.revisionThird),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        minFontSize = 20.sp,
                    )
                }

                NavigationDestination.AboutScreen.destination -> {
                    TitleAbout()
                }

                NavigationDestination.CertificateScreen.destination -> {
                    Text(
                        text = stringResource(id = R.string.certificate),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        },
        navigationIcon = {
            if (currentScreen != NavigationDestination.LettersScreen.destination
                && currentScreen != NavigationDestination.SplashScreen.destination
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    onDividerVisibilityChange(false)
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
                NavigationDestination.LettersScreen.destination -> {
                    DialogInfo(
                        title = stringResource(id = R.string.infoLettersScreen)
                    )
                    DialogGift(
                        isLettersCompleted = isLettersCompleted,
                        navController = navController,
                        isNameNotEmpty = isNameNotEmpty,
                        name = name,
                        timeLearning = timeLearning
                    )
                    AboutMenu {
                        navController.navigate(NavigationDestination.AboutScreen.destination)
                    }
                }

                NavigationDestination.TasksScreen.destination -> DialogInfo(
                    title = stringResource(id = R.string.infoTasksScreen, letter!!)
                )

                NavigationDestination.FirstTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoFirstScreen, letter!!
                    )
                )

                NavigationDestination.SecondTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoSecondScreen, letter!!
                    )
                )

                NavigationDestination.ThirdTaskScreen.destination -> DialogInfo(
                    title = stringResource(
                        id = R.string.infoThirdScreen
                    )
                )

                NavigationDestination.FourthTaskScreen.destination -> DialogInfo(
                    title = stringResource(id = R.string.infoFourthScreen)
                )

                NavigationDestination.RevisionTaskScreen.destination -> DialogInfo(
                    title = stringResource(id = R.string.infoRevisionTasksScreen)
                )

                NavigationDestination.RevisionFirstScreen.destination -> DialogInfo(
                    title = stringResource(id = R.string.infoRevisionFirstScreen)
                )

                NavigationDestination.RevisionSecondScreen.destination -> DialogInfo(
                    title = stringResource(id = R.string.infoRevisionSecondScreen)
                )

                NavigationDestination.RevisionThirdScreen.destination -> DialogInfo(
                    title = stringResource(id = R.string.infoThirdScreen)
                )

                NavigationDestination.CertificateScreen.destination -> DownloadButton()
            }
        }
    )
}

@Composable
fun TitleAbout() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AutoSizeText(
            text = stringResource(id = R.string.aboutTitle),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            fontSize = 22.sp,
            minFontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.aboutSubTitle),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal,
        )
    }
}