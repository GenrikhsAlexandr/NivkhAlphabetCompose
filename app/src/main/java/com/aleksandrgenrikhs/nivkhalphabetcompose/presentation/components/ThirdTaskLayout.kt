package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun ThirdTaskLayout(
    modifier: Modifier = Modifier,
    words: List<ThirdTaskModel>,
    onIconClick: (String) -> Unit,
    onDone: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = modifier
                    .weight(1f)
                    .background(colorPrimary)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(words) {
                    IconButton(
                        icon = it.icon,
                        onClick = { onIconClick(it.wordId) },
                    )
                }
            }
            LazyColumn(
                modifier = modifier
                    .background(colorPrimary)
                    .weight(1f)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val shuffledTitle = words.shuffled()
                items(shuffledTitle) {
                    ContainerText(
                        title = it.title,
                    )
                }
            }
        }
        SubmitButton(
            word = "",
            onDone = onDone
        )
    }
}

@Composable
private fun ContainerText(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(250.dp)
            .border(
                width = 2.dp,
                color = colorProgressBar
            )
            .background(colorOnPrimary)
            .padding(horizontal = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        AutoSizeText(
            text = title,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.displayMedium,
            minFontSize = 32.sp,
        )
    }
}

@Composable
private fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: String?,

    ) {
    IconButton(
        modifier = modifier
            .size(250.dp)
            .border(
                width = 2.dp,
                color = colorProgressBar
            )
            .background(colorOnPrimary),
        onClick = onClick
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
}

@Composable
private fun SubmitButton(
    word: String,
    onDone: (word: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            onDone(word)
        },
        modifier = modifier
            .wrapContentSize(),
        colors = ButtonColors(
            colorRight,
            colorRight,
            colorRight,
            colorRight
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.submit),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
}