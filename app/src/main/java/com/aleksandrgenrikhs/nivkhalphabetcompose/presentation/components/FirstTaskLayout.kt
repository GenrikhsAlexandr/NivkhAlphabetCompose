package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun FirstTaskLayout(
    modifier: Modifier = Modifier,
    title: List<String>,
    wordId: List<String>,
    icon: List<String?>,
    progress: List<Int>,
    isClickable: List<Boolean>,
    letter: String,
    onClick: (String, Int?) -> Unit,
    isClickableLetter: Boolean,
    isPlaying: Boolean,
    progressLetter: Int,
    isVisibleWord: Boolean,
    getWordError: Boolean
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    )
    {
        item {
            Text(
                text = stringResource(id = R.string.titleFistTask),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        item {
            CardLetter(
                progress = progressLetter,
                title = letter,
                onClick = {
                    onClick(letter, null)
                },
                isClickable = isClickableLetter && !isPlaying,
            )
        }
        if (title.isNotEmpty()) {
            itemsIndexed(title) { index, title ->
                CardWord(
                    progress = progress[index],
                    title = title,
                    icon = icon[index],
                    onClick = { onClick(wordId[index], index) },
                    isClickable = isClickable[index] && !isPlaying,
                    isVisible = isVisibleWord,
                    getWordError = getWordError
                )
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
    progress: Int
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
    getWordError: Boolean,
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
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(130.dp)
                        .align(Alignment.CenterVertically),
                ) {
                    val state = painter.state
                    when (state) {
                        is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                        is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                        else -> Icon(
                            imageVector = Icons.Default.Warning,
                            tint = colorText,
                            contentDescription = null,
                        )
                    }
                }
                AutoSizeText(
                    text = if (getWordError) stringResource(id = R.string.getWordError) else title,
                    maxLines = 1,
                    style = MaterialTheme.typography.displayMedium,
                    minFontSize = 32.sp,
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
        FirstTaskLayout(
            title = listOf("Alpha", "Word", "Nivkh"),
            wordId = listOf("1.2", "1.3", "1.1"),
            icon = listOf("we", "wew", "ds"),
            progress = listOf(3, 2, 0),
            isClickable = listOf(true, false, false),
            letter = "Aa",
            onClick = { _, _ -> },
            isClickableLetter = false,
            progressLetter = 1,
            isVisibleWord = true,
            getWordError = false,
            isPlaying = false,
        )
    }
}