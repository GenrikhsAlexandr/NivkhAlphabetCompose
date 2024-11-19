package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.LazyListScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ShowDividerWhenScrolled

@OptIn(ExperimentalMaterial3Api::class)
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
    onBack: () -> Unit
) {
    var isDividerVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scrollableState: ScrollableState = LazyListScrollableState(listState)
    val savedScrollPosition = rememberSaveable { listState.firstVisibleItemScrollOffset }
    ShowDividerWhenScrolled(
        onDividerVisibilityChange = { isVisible ->
            isDividerVisible = isVisible
        },
        scrollableState = scrollableState
    )

// Восстанавливаем позицию прокрутки при загрузке экрана
    LaunchedEffect(Unit) {
        listState.scrollToItem(savedScrollPosition)
    }
    val action: @Composable RowScope.() -> Unit = {
        DialogInfo(title = stringResource(id = R.string.infoRevisionSecondScreen))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarTask(
                        title = stringResource(id = R.string.revisionSecond),
                        actions = action,

                        ),
                    navigation = onBack
                )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .background(colorPrimary)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
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
            onBack = {}
        )
    }
}