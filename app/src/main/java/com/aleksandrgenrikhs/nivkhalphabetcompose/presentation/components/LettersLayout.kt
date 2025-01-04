package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.LettersUIState
import com.idapgroup.autosizetext.AutoSizeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LettersLayout(
    state: LettersUIState,
    navController: NavController,
    onClickLetter: (String) -> Unit,
    onClickRevision: () -> Unit,
) {
    var isDividerVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyGridState()

    LaunchedEffect(listState) {
        var previousDividerVisible = isDividerVisible
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { offset ->
                val isCurrentlyVisible = offset > 0
                if (isCurrentlyVisible != previousDividerVisible) {
                    previousDividerVisible = isCurrentlyVisible
                    isDividerVisible = isCurrentlyVisible
                }
            }
    }

    val actions: @Composable RowScope.() -> Unit = {
        DialogInfo(
            title = stringResource(id = R.string.infoLettersScreen)
        )
        DialogGift(
            isLettersCompleted = state.isAllLettersCompleted,
            navController = navController,
            isCertificateCreated = state.isCertificateCreated,
            timeLearning = state.timeLearning
        )
        DropdownMenu {
            navController.navigate(NavigationDestination.AboutScreen.destination)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarLetters(
                        title = stringResource(id = R.string.appName),
                        actions = actions,
                    )
                )
                if (isDividerVisible) {
                    HorizontalDivider(
                        color = colorText
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(colorPrimary),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Adaptive(70.dp),
        ) {
            if (state.letters.isNotEmpty()) {
                itemsIndexed(state.letters) { index, item ->
                    LetterItem(
                        letter = item,
                        onClick = {
                            onClickLetter(item)
                        },
                        isCompleted = state.isLetterCompleted[index]
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    RepeatItem(
                        onClick = {
                            onClickRevision()
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun LetterItem(
    letter: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCompleted: Boolean,
) {
    val textColor = if (isCompleted) colorProgressBar else colorText
    Box(
        modifier = modifier
            .size(70.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
    ) {
        AutoSizeText(
            text = letter,
            color = textColor,
            maxLines = 1,
            style = MaterialTheme.typography.displayMedium,
            minFontSize = 35.sp,
            textAlign = TextAlign.Unspecified,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun RepeatItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clip(ShapeDefaults.Medium)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_repeat),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(130.dp)
        )
        Text(
            text = stringResource(id = R.string.revisionTask),
            modifier = modifier
                .padding(8.dp),
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewContent(
    @PreviewParameter(LettersUiStates::class)
    uiState: LettersUIState,
) {
    NivkhAlphabetComposeTheme {
        LettersLayout(
            state = uiState,
            navController = rememberNavController(),
            onClickLetter = {},
            onClickRevision = {},
        )
    }
}

private class LettersUiStates :
    PreviewParameterProvider<LettersUIState> {
    override val values: Sequence<LettersUIState>
        get() = sequenceOf(
            LettersUIState(
                letters = Letters.entries.map { it.title },
                isLetterCompleted = List(Letters.entries.size) { false },
            )
        )
}
