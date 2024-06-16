package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.thirdtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar

@Composable
fun TextThirdTask(
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
        Text(
            text = stringResource(id = R.string.titleThirdTask) ,
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonThirdTask(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: String?,

    ) {
    IconButton(
        modifier = modifier
            .size(180.dp)
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

@Preview(widthDp = 400, heightDp = 210)
@Composable
fun TextPreview() {
    NivkhAlphabetComposeTheme {
        TextThirdTask(
            letter = Letters.A.title
        )
    }
}
@Preview(widthDp = 210, heightDp = 210)
@Composable
fun ButtonElementPreview() {
    NivkhAlphabetComposeTheme {
        ButtonThirdTask(
            icon = "file:///android_asset/firsttask/image/1.1.png",
            onClick = {}
        )
    }
}