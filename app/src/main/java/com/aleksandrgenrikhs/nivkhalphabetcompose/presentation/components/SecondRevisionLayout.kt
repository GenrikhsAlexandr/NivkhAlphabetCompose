package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.LazyListScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ShowDividerWhenScrolled

@Composable
fun SecondRevisionLayout(
    words: List<String>,
    wordsId: List<String>,
    correctWordId: String,
    icon: String?,
    modifier: Modifier = Modifier,
    onWordClick: (String) -> Unit,
    onIconClick: (String) -> Unit,
    isCorrectAnswer: List<Boolean?>,
    isClickable: Boolean,
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    val scrollableState: ScrollableState = LazyListScrollableState(listState)

    ShowDividerWhenScrolled(onDividerVisibilityChange, scrollableState)

    LazyColumn(
        modifier = modifier
            .background(colorPrimary)
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            IconButton(
                onClick = {
                    onIconClick("$WORDS_AUDIO$correctWordId")
                },
                icon = icon
            )
        }
        itemsIndexed(words) { index, item ->
            WordItem(
                title = item,
                onClick = {
                    onWordClick(wordsId[index])
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
    icon: String?
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun WordItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCorrectAnswer: Boolean?,
    isClickable: Boolean
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(CircleShape)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isCorrectAnswer != null && !isCorrectAnswer) colorError else if (isCorrectAnswer == null) colorText else colorProgressBar,
            maxLines = 1,
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun SecondRevisionLayoutPreview() {
    NivkhAlphabetComposeTheme {
        SecondRevisionLayout(
            words = listOf("Aa", "Bb", "Cc"),
            wordsId = listOf("1", "2", "3"),
            icon = null,
            correctWordId = "1",
            onIconClick = {},
            onWordClick = {},
            isCorrectAnswer = listOf(null, true, false),
            isClickable = true,
            onDividerVisibilityChange = {}
        )
    }
}