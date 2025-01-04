package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskLearnLetterUIState
import com.idapgroup.autosizetext.AutoSizeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskLearnLetterLayout(
    modifier: Modifier = Modifier,
    viewState: TaskLearnLetterUIState,
    letter: String,
    onClick: (String, Int?) -> Unit,
    onBack: () -> Unit,
) {
    var isDividerVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

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

    val action: @Composable RowScope.() -> Unit = {
        DialogInfo(title = stringResource(id = R.string.infoFirstScreen, letter))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarTask(
                        title = stringResource(id = R.string.firstTask),
                        actions = action,

                        ),
                    navigation = onBack
                )
                if (isDividerVisible) {
                    HorizontalDivider(
                        color = colorText
                    )
                }
            }
        }
    ) { paddingValues ->

        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        )
        {
            item {
                Text(
                    text = stringResource(id = R.string.titleFirstTask),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            item {
                CardLetter(
                    progress = viewState.progressLetter,
                    title = letter,
                    onClick = {
                        onClick(letter, null)
                    },
                    isClickable = viewState.isClickableLetter && !viewState.isPlaying,
                )
            }
            if (viewState.titles.isNotEmpty()) {
                itemsIndexed(viewState.titles) { index, title ->
                    CardWord(
                        progress = viewState.progressWords[index],
                        title = title,
                        icon = viewState.icons[index],
                        onClick = { onClick(viewState.wordsId[index], index) },
                        isClickable = viewState.isClickableWords[index] && !viewState.isPlaying,
                        isVisible = viewState.isVisibleWord,
                    )
                }
            }
        }
    }
}

@Composable
private fun CardLetter(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    isClickable: Boolean,
    progress: Int,
) {
    Box(
        modifier = modifier
            .size(210.dp)
            .clip(ShapeDefaults.Medium)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    )
    {
        LinearProgressIndicator(
            progress = { (progress.toFloat() / 5f) },
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .rotate(-90f),
            trackColor = colorCardLetterItem,
            color = colorProgressBar
        )
        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 90.sp
            )
        )
    }
}

@Composable
private fun CardWord(
    modifier: Modifier = Modifier,
    progress: Int,
    title: String,
    icon: String?,
    onClick: () -> Unit,
    isClickable: Boolean,
    isVisible: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
            .background(colorPrimary)
            .clip(ShapeDefaults.Medium)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    )
    {
        LinearProgressIndicator(
            progress = { (progress.toFloat() / 3f) },
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            trackColor = colorCardLetterItem,
            color = colorProgressBar
        )
        if (isVisible) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                AsyncImage(
                    model = icon,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(130.dp)
                        .align(Alignment.CenterVertically),
                )
                AutoSizeText(
                    text = title,
                    maxLines = 1,
                    style = MaterialTheme.typography.displayMedium,
                    minFontSize = 28.sp,
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 8.dp)
                )
            }
        }
        if (!isVisible) {
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

@Preview(widthDp = 410, heightDp = 610)
@Composable
private fun FirstTaskContentPreview() {
    NivkhAlphabetComposeTheme {
        TaskLearnLetterLayout(
            viewState = TaskLearnLetterUIState(
                titles = listOf("word1", "word2", "word3"),
                progressWords = listOf(1, 2, 3),
            ),
            letter = "Aa",
            onClick = { _, _ -> },
            onBack = {}
        )
    }
}
