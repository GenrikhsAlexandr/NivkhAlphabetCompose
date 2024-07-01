package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary

@Composable
fun FourthTaskLayout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onDone: (String) -> Unit,
    icon: String?,
    onUserGuessChanged: (String) -> Unit,
    isGuessWrong: Boolean,
    userGuess: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(180.dp)
                .background(colorCardLetterItem)
                .clickable(
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
        }
    }
    NivkhKeyboard(
        input = userGuess,
        isError = isGuessWrong,
        onValueChange = onUserGuessChanged,
        onDelete = onDelete,
        onDone = onDone,
        modifier = Modifier
    )
}