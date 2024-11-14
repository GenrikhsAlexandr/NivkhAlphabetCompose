package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.idapgroup.autosizetext.AutoSizeText

object AppBar {

    sealed class AppBarConfig {
        data class AppBarLetters(
            val title: String,
            val actions: @Composable RowScope.() -> Unit,
        ) : AppBarConfig()

        data class AppBarTask(
            val title: String,
            val actions: @Composable RowScope.() -> Unit
        ) : AppBarConfig()

        data object AppBarAbout : AppBarConfig()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Render(
        config: AppBarConfig,
        colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorPrimary,
        ),
        modifier: Modifier = Modifier,
        windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
        navigationIcon: ImageVector = Icons.AutoMirrored.Default.ArrowBack,
        navigation: (() -> Unit)? = null,
    ) {
        val title = buildTitle(config)
        val actions = buildActions(config)
        val navigationIconComposable = @Composable {
            when {
                navigation != null -> {
                    IconButton(onClick = navigation) {
                        Icon(
                            imageVector = navigationIcon,
                            tint = colorText,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }
        }

        CenterAlignedTopAppBar(
            modifier = modifier,
            colors = colors,
            title = title,
            navigationIcon = navigationIconComposable,
            actions = actions,
            windowInsets = windowInsets,
        )
    }

    private fun buildTitle(
        config: AppBarConfig,
    ): @Composable () -> Unit {
        val title = @Composable {
            Row {
                when (config) {
                    is AppBarConfig.AppBarLetters -> {
                        AutoSizeText(
                            text = config.title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            minFontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    is AppBarConfig.AppBarTask -> {
                        AutoSizeText(
                            text = config.title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            minFontSize = 16.sp,
                        )
                    }

                    is AppBarConfig.AppBarAbout -> {
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
                }
            }
        }
        return title
    }

    private fun buildActions(
        config: AppBarConfig,
    ): @Composable (RowScope.() -> Unit) {
        val actions: @Composable RowScope.() -> Unit = {
            when (config) {
                is AppBarConfig.AppBarLetters -> {
                    config.actions(this)
                }

                is AppBarConfig.AppBarTask -> {
                    config.actions(this)
                }

                is AppBarConfig.AppBarAbout -> {
                    Unit
                }
            }
        }
        return actions
    }
}