package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.LetterModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.shapes
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun LettersLayout(
    letters: List<LetterModel>,
    modifier: Modifier = Modifier,
    isVisibleRepeat: Boolean,
    onClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(colorPrimary)
            .padding(
                start = 8.dp,
                end = 8.dp,
            ),
        contentPadding = PaddingValues(vertical = 8.dp),
        columns = GridCells.Adaptive(100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(letters, key = { letter -> letter.letter.title }) { item ->
            with(item) {
                LetterItem(
                    letter = letter.title,
                    onClick = { onClick(letter.title) },
                    isClickable = isLetterCompleted
                )
            }
        }
        item {
            RepeatItem(
                onClick = {},
                isClickable = false,
                isVisible = isVisibleRepeat,
            )
        }
    }
}


@Composable
fun LetterItem(
    letter: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClickable: Boolean,
) {
    val backgroundColor = if (isClickable) colorProgressBar else colorCardLetterItem

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .size(100.dp)
            .border(width = 1.dp, color = colorProgressBar, shape = shapes.small)
            .clickable(onClick = onClick),
        shape = shapes.small
    ) {
        Box(
            modifier = modifier
                .background(backgroundColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            AutoSizeText(
                text = letter,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.displayLarge,
                minFontSize = 50.sp,
            )
        }
    }
}

@Composable
fun RepeatItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClickable: Boolean,
    isVisible: Boolean,

    ) {
    val backgroundColor = if (isClickable) colorProgressBar else colorCardLetterItem

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .wrapContentWidth()
            .height(100.dp)
            .border(width = 1.dp, color = colorProgressBar, shape = shapes.small)
            .clickable(
                enabled = isVisible,
                onClick = onClick
            ),
        shape = shapes.small
    ) {
        Box(
            modifier = modifier
                .background(backgroundColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            if (isVisible) {
                AutoSizeText(
                    text = stringResource(id = R.string.otherTask),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    minFontSize = 16.sp,
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
}

@Preview(widthDp = 500, heightDp = 700)
@Composable
fun LetterElementPreview() {
    NivkhAlphabetComposeTheme {
        LettersLayout(
            letters = listOf(LetterModel(Letters.A, false), LetterModel(Letters.B, true)),
            modifier = Modifier,
            onClick = {},
            isVisibleRepeat = false,
        )
    }
}