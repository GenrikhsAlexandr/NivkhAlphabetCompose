package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun LettersLayout(
    modifier: Modifier = Modifier,
    letters: List<String>,
    isLetterCompleted: List<Boolean>,
    onClickLetter: (String) -> Unit,
    onClickRevision: () -> Unit,
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
                    onClick = { onClickLetter(item) },
                    isCompleted = isLetterCompleted[index]
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                RepeatItem(
                    modifier = Modifier
                        .wrapContentSize(
                            unbounded = true,
                        ),
                    onClick = {
                        onClickRevision()
                    },
                )
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
            .fillMaxSize()
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AutoSizeText(
            text = letter,
            color = textColor,
            maxLines = 1,
            style = MaterialTheme.typography.displayMedium,
            minFontSize = 38.sp,
            modifier = modifier.padding(8.dp)
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
            .clickable(
                onClick = onClick,
            ),
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

@Preview(widthDp = 500, heightDp = 700)
@Composable
private fun LetterElementPreview() {
    NivkhAlphabetComposeTheme {
        LettersLayout(
            letters = listOf("Щщ", "Шш", "Юю"),
            isLetterCompleted = listOf(true, false, false),
            modifier = Modifier,
            onClickLetter = {},
            onClickRevision = {}
        )
    }
}