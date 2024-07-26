package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aleksandrgenrikhs.nivkhalphabetcompose.LettersKeybord
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun NivkhKeyboard(
    modifier: Modifier = Modifier,
    input: String,
    letter: List<String> = LettersKeybord.entries.map { it.title },
    isError: Boolean,
    onValueChange: (letter: String) -> Unit,
    onDelete: () -> Unit,
    onDone: (word: String) -> Unit,
    onClickable: Boolean
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DeleteButton(
                onDelete = onDelete
            )
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .size(60.dp)
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .border(
                        shape = RoundedCornerShape(8.dp),
                        brush = if (!isError) SolidColor(colorProgressBar) else SolidColor(
                            colorError
                        ),
                        width = 1.dp
                    ),
                contentAlignment = Alignment.Center,
            )
            {
                AutoSizeText(
                    text = input,
                    maxLines = 1,
                    style = MaterialTheme.typography.displayMedium,
                    minFontSize = 32.sp,
                    color = if (!isError) colorText else colorError,
                    modifier = Modifier
                        .padding(4.dp),
                )
            }
            DoneButton(
                onDone = onDone,
                word = input,
                onClickable = onClickable
            )
        }
    }
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
    ) {
        items(letter) {
            LetterButton(
                letter = it,
                onClick = onValueChange,
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            SpaceButton(
                modifier = Modifier
                    .wrapContentSize(
                        unbounded = true,
                    ),
                onClick = onValueChange
            )
        }
    }
}

@Composable
private fun LetterButton(
    letter: String,
    onClick: (letter: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(colorPrimary)
            .clip(CircleShape)
            .clickable(onClick = {
                onClick(letter)
            }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier
                .padding(horizontal = 16.dp),
            text = letter,
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Composable
private fun DeleteButton(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .height(60.dp)
            .wrapContentWidth()
            .clickable(onClick = {
                onDelete()
            }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.align(Alignment.Center),
            fontWeight = FontWeight.ExtraBold,
            text = "←",
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Composable
private fun DoneButton(
    word: String,
    onDone: (word: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickable: Boolean
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .wrapContentWidth()
            .clip(CircleShape)
            .clickable(
                onClick = {
                    onDone(word)
                },
                enabled = onClickable
            ),
        contentAlignment = Alignment.Center
        ) {
        Text(
            text = "✓",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
            color = colorProgressBar
        )
    }
}


@Composable
private fun SpaceButton(
    modifier: Modifier = Modifier,
    onClick: (space: String) -> Unit,
    space: String = "\u2005"
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(colorPrimary)
            .clip(CircleShape)
            .clickable(onClick = {
                onClick(space)
            }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.space),
            style = MaterialTheme.typography.displayMedium,
        )
    }
}