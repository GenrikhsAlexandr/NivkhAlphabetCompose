package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.shapes

@Composable
fun CardLetter(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    isClickable: Boolean,
    progress: Int
) {
    Text(
        text = stringResource(id = R.string.titleFistTask) ,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )

    ElevatedCard(
        modifier = modifier
            .size(210.dp)
            .aspectRatio(1f)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            )
            .padding(8.dp),
        shape = shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    )
    {
        Box(
            modifier = modifier
                .fillMaxSize(),
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
}

@Composable
fun CardWord(
    modifier: Modifier = Modifier,
    progress: Int,
    title: String,
    icon: String?,
    onClick: () -> Unit,
    isClickable: Boolean,
    isVisible: Boolean,
    getWordError: Boolean,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            ),
        shape = shapes.medium,
    ) {
        Box(
            modifier = modifier
                .background(colorOnPrimary)
                .fillMaxSize(),
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
                    if (getWordError) {
                        Text(
                            text = stringResource(id = R.string.getWordError),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp)
                        )
                    } else {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp)
                        )
                    }
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
}

@Preview(widthDp = 210, heightDp = 210)
@Composable
fun CardLetterPreview() {
    NivkhAlphabetComposeTheme {
        CardLetter(
            title = Letters.Shch.title,
            onClick = {},
            isClickable = true,
            progress = 3
        )
    }
}

@Preview(widthDp = 400, heightDp = 200)
@Composable
fun CardWord1Preview() {
    NivkhAlphabetComposeTheme {
        CardWord(
            title = "nivkh word",
            onClick = {},
            isClickable = true,
            progress = 2,
            isVisible = false,
            icon = "file:///android_asset/firsttask/image/1.1.png",
            getWordError = true
        )
    }
}