package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondTaskLayout(
    modifier: Modifier = Modifier,
    lettersId: List<String>,
    titles: List<String>,
    wordsId: List<String>,
    icons: List<String?>,
    isFlipped: List<Boolean>,
    isCorrectAnswer: List<Boolean>,
    letter: String,
    onClick: (String, String) -> Unit,
    isClickable: Boolean,
    onBack: () -> Unit
) {
    var isDividerVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val action: @Composable RowScope.() -> Unit = {
        DialogInfo(title = stringResource(id = R.string.infoSecondScreen, letter))
    }

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

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarTask(
                        title = stringResource(id = R.string.secondTask),
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
                .background(colorPrimary)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            item {
                TitleTask(
                    letter = letter
                )
            }
            itemsIndexed(titles) { index, item ->
                IconButton(
                    onClick = {
                        onClick(wordsId[index], lettersId[index])
                    },
                    icon = icons[index],
                    title = item,
                    isFlipped = isFlipped[index],
                    isClickable = isClickable,
                    isCorrectAnswer = isCorrectAnswer[index],
                )
            }
        }
    }
}

@Composable
private fun TitleTask(
    modifier: Modifier = Modifier,
    letter: String,
) {
    Box(
        modifier = modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    )
    {
        val text = stringResource(id = R.string.titleSecondTask, letter)
        val annotatedString = buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.ExtraBold,
                    color = colorText,
                ),
                start = text.indexOf(letter),
                end = text.indexOf(letter) + letter.length
            )
        }
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: String?,
    title: String,
    isFlipped: Boolean,
    isClickable: Boolean,
    isCorrectAnswer: Boolean
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f, label = "",
    )
    Crossfade(
        targetState = isFlipped, label = ""
    ) { flipped ->
        Box(
            modifier = modifier
                .clickable(
                    enabled = isClickable,
                    onClick = onClick
                )
                .size(180.dp)
                .clip(ShapeDefaults.Medium)
                .background(
                    if (isCorrectAnswer && flipped) {
                        colorRight
                    } else
                        if (!flipped) {
                            colorCardLetterItem
                        } else {
                            colorError
                        }
                )
                .graphicsLayer(
                    rotationY = rotationAngle,
                    transformOrigin = TransformOrigin.Center,
                ),
            contentAlignment = Alignment.Center
        ) {
            if (!flipped) {
                AsyncImage(
                    model = icon,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier.fillMaxSize()
                )
            } else {
                Text(
                    modifier = modifier.graphicsLayer {
                        rotationY = rotationAngle
                        transformOrigin = TransformOrigin.Center
                    },
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

@Preview(widthDp = 410, heightDp = 610)
@Composable
private fun SecondTaskPreview() {
    NivkhAlphabetComposeTheme {
        SecondTaskLayout(
            lettersId = listOf("Aa", "Bb", "Cc"),
            titles = listOf("Hello", "Word", "Nivkh"),
            wordsId = listOf("1.2", "1.1", "1.3"),
            icons = listOf(null, null, null),
            isFlipped = listOf(true, false, true),
            letter = "Aa",
            onClick = { _, _ -> },
            isClickable = true,
            isCorrectAnswer = listOf(true, true, false),
            onBack = {}
        )
    }
}