package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary

@Composable
fun FourthTaskLayout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onDone: (String) -> Unit,
    icon: String?,
    onUserGuessChanged: (String) -> Unit,
    isGuessWrong: Boolean,
    userGuess: String,
    onClickable: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = modifier
                .size(180.dp)
                .background(colorCardLetterItem)
                .clickable(
                    onClick = onClick
                )
                .border(
                    width = 2.dp,
                    color = colorPrimary
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = icon,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier.fillMaxSize()
            )
        }
        NivkhKeyboard(
            input = userGuess,
            isError = isGuessWrong,
            onValueChange = onUserGuessChanged,
            onDelete = onDelete,
            onDone = onDone,
            onClickable = onClickable
        )
    }
}

@Preview(widthDp = 510, heightDp = 1010)
@Composable
private fun FourthTaskContentPreview() {
    NivkhAlphabetComposeTheme {
        FourthTaskLayout(
            onClick = { },
            onDelete = {},
            onDone = {},
            icon = null,
            onUserGuessChanged = {},
            isGuessWrong = true,
            userGuess = "Aldjf",
            onClickable = true
        )
    }
}