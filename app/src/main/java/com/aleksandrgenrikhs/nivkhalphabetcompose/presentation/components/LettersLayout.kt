package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@Composable
fun LettersLayout(
    modifier: Modifier = Modifier,
    letters: List<String>,
    isLetterCompleted: List<Boolean>,
    isVisibleRepeat: Boolean,
    onClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(colorPrimary)
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
        contentPadding = PaddingValues(vertical = 8.dp),
        columns = GridCells.Adaptive(80.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (letters.isNotEmpty()) {
            itemsIndexed(letters) { index, item ->
                LetterItem(
                    letter = item,
                    onClick = { onClick(item) },
                    isCompleted = isLetterCompleted[index]
                )
            }
            item {
                RepeatItem(
                    onClick = {},
                    isVisible = isVisibleRepeat,
                )
            }
        }
    }
}

@Composable
fun LetterItem(
    letter: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCompleted: Boolean,
) {
    val textColor = if (isCompleted) colorProgressBar else colorText
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            modifier = modifier
                .wrapContentSize()
                .padding(8.dp),
            color = textColor,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Composable
fun RepeatItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,

    ) {

    Box(
        modifier = modifier
            .wrapContentSize()
            .background(colorPrimary)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                enabled = isVisible
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isVisible) {
            Text(
                text = stringResource(id = R.string.otherTask),
                modifier = modifier
                    .wrapContentSize()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.displayMedium,
            )
        } else {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = colorText,
                modifier = modifier
                    .size(50.dp)
            )
        }
    }
}

@Preview(widthDp = 500, heightDp = 700)
@Composable
fun LetterElementPreview() {
    NivkhAlphabetComposeTheme {
        LettersLayout(
            letters = listOf("Aa", "Bb", "Cc"),
            isLetterCompleted = listOf(true, false, false),
            modifier = Modifier,
            onClick = {},
            isVisibleRepeat = true,
        )
    }
}