package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.secondtask

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@Composable
fun TextSecondTask(
    modifier: Modifier = Modifier,
    letter: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
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
            modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
        )
    }
}

@Composable
fun ButtonSecondTask(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: String?,
    title: String,
    isFlipped: Boolean,
    isClickable: Boolean,
    isRightAnswer: Boolean

) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f, label = "",
    )
    Box(
        modifier = modifier
            .clickable { onClick() }
            .size(180.dp)
            .background(colorCardLetterItem)
            .graphicsLayer(
                rotationY = rotationAngle,
                transformOrigin = TransformOrigin.Center,
            ),

        contentAlignment = Alignment.Center
    ) {
        if (!isFlipped) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = isClickable,
                        onClick = onClick
                    )
                    .border(
                        width = 2.dp,
                        color = colorPrimary
                    ),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                ) {
                    val state = painter.state
                    when (state) {
                        is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                        is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                        else -> Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                        )
                    }
                }
            }
        } else {
            if (isRightAnswer) {
                Box(
                    modifier = modifier
                        .clickable(
                            enabled = isClickable,
                            onClick = onClick
                        )
                        .graphicsLayer(
                            rotationY = rotationAngle,
                            transformOrigin = TransformOrigin.Center,
                        )
                        .background(colorRight)
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = colorPrimary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            } else {
                Box(
                    modifier = modifier
                        .clickable(
                            enabled = isClickable,
                            onClick = onClick
                        )
                        .graphicsLayer(
                            rotationY = rotationAngle,
                            transformOrigin = TransformOrigin.Center,
                        )
                        .background(colorError)
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = colorPrimary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 400, heightDp = 210)
@Composable
fun TextPreview() {
    NivkhAlphabetComposeTheme {
        TextSecondTask(
            letter = Letters.A.title
        )
    }
}

@Preview(widthDp = 210, heightDp = 210)
@Composable
fun ButtonElementPreview() {
    NivkhAlphabetComposeTheme {
        ButtonSecondTask(
            icon = "file:///android_asset/firsttask/image/1.1.png",
            onClick = {},
            title = "dd",
            isFlipped = true,
            isClickable = true,
            isRightAnswer = true
        )
    }
}