package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskWriteWordUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskWriteWordLayout(
    modifier: Modifier = Modifier,
    viewState: TaskWriteWordUIState,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onDone: () -> Unit,
    onInputChange: (String) -> Unit,
    onBack: () -> Unit,
) {
    val action: @Composable RowScope.() -> Unit = {
        DialogInfo(title = stringResource(id = R.string.infoFourthScreen))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBar.Render(
                config = AppBar.AppBarConfig.AppBarTask(
                    title = stringResource(id = R.string.fourthTask),
                    actions = action,
                ),
                navigation = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary)
                .padding(top = paddingValues.calculateTopPadding() + 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = modifier
                    .size(180.dp)
                    .clip(ShapeDefaults.Medium)
                    .background(colorCardLetterItem)
                    .clickable(
                        onClick = onClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = viewState.icon,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier.fillMaxSize()
                )
            }
            NivkhKeyboard(
                input = viewState.inputWord,
                isError = viewState.isGuessWrong,
                onInputChange = onInputChange,
                onDelete = onDelete,
                onDone = onDone,
                onClickable = viewState.isClickable
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TaskWriteWordContentPreview() {
    NivkhAlphabetComposeTheme {
        TaskWriteWordLayout(
            viewState = TaskWriteWordUIState(),
            onClick = { },
            onDelete = {},
            onDone = {},
            onInputChange = {},
            onBack = {}
        )
    }
}
