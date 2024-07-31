package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTER_AUDIO

@Composable
fun RevisionFirstLayout(
    letters: List<String>,
    correctLetter: String,
    modifier: Modifier = Modifier,
    onLetterClick: (String) -> Unit,
    onIconClick: (String) -> Unit,
    isCorrectAnswer: List<Boolean?>,
    isClickable: Boolean
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(colorPrimary)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    )
    {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Image(
                painter = painterResource(id = R.drawable.ic_revision_first),
                contentDescription = null,
                modifier = modifier
                    .size(200.dp),
                alignment = Alignment.Center
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            IconButton(
                onClick = {
                    onIconClick("$LETTER_AUDIO$correctLetter")
                }
            )
        }
        itemsIndexed(letters) { index, item ->
            LetterItem(
                letter = item,
                onClick = {
                    onLetterClick(item)
                },
                isCorrectAnswer = isCorrectAnswer[index],
                isClickable = isClickable
            )

        }
    }
}

@Composable
private fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_revision_first_sound),
            contentDescription = null,
            tint = colorText,
            modifier = modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun LetterItem(
    letter: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCorrectAnswer: Boolean?,
    isClickable: Boolean
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(CircleShape)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            color = if (isCorrectAnswer != null && !isCorrectAnswer) colorError else if (isCorrectAnswer == null) colorText else colorProgressBar,
            maxLines = 1,
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun RevisionFirstLayoutPreview() {
    NivkhAlphabetComposeTheme {
        RevisionFirstLayout(
            letters = listOf("Aa", "Bb", "Cc"),
            correctLetter = "Aa",
            onIconClick = {},
            onLetterClick = {},
            isCorrectAnswer = listOf(null, true, false),
            isClickable = true

        )
    }
}