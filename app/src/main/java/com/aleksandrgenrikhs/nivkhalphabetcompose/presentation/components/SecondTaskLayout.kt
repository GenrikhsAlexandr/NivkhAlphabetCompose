package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@Composable
fun SecondTaskLayout(
    modifier: Modifier = Modifier,
    letterId: List<String>,
    title: List<String>,
    wordId: List<String>,
    icon: List<String?>,
    isFlipped: List<Boolean>,
    isCorrectAnswer: List<Boolean>,
    letter: String,
    onClick: (String, String) -> Unit,
    isClickable: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TitleTask(
            letter = letter
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            itemsIndexed(title) { index, item ->
                IconButton(
                    onClick = {
                        onClick(wordId[index], letterId[index])
                    },
                    icon = icon[index],
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
    Box(
        modifier = modifier
            .clickable { onClick() }
            .size(180.dp)
            .clip(ShapeDefaults.Medium)
            .background(colorCardLetterItem)
            .graphicsLayer(
                rotationY = rotationAngle,
                transformOrigin = TransformOrigin.Center,
            ),

        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .size(180.dp)
                .clickable(
                    enabled = isClickable,
                    onClick = onClick
                )
                .background(
                    if (isCorrectAnswer && isFlipped) {
                        colorRight
                    } else
                        if (!isFlipped) {
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
            if (!isFlipped) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .crossfade(true)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                        is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                        else -> Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                        )
                    }
                }
            } else {
                Text(
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
            letterId = listOf("Aa", "Bb", "Cc"),
            title = listOf("Hello", "Word", "Nivkh"),
            wordId = listOf("1.2", "1.1", "1.3"),
            icon = listOf(null, null, null),
            isFlipped = listOf(true, false, true),
            letter = "Aa",
            onClick = { _, _ -> },
            isClickable = true,
            isCorrectAnswer = listOf(true, true, false),
        )
    }
}