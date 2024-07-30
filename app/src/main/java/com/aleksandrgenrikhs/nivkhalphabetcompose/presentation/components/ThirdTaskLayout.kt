package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun ThirdTaskLayout(
    modifier: Modifier = Modifier,
    titles: List<String>,
    wordsId: List<String>,
    icons: List<String?>,
    shareWords: List<String?>,
    currentWords: List<String?>,
    onIconClick: (String) -> Unit,
    isGuessWrong: Boolean,
    onDone: () -> Unit,
    onDragAndDropEventReceived: (DragAndDropEvent, Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (titles.isNotEmpty()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    icon = icons[0],
                    onClick = { onIconClick("$WORDS_AUDIO${wordsId[0]}") },
                )
                Spacer(modifier = modifier.width(4.dp))
                ReceivingContainer(
                    title = currentWords[0] ?: "",
                    index = 0,
                    onDragAndDropEventReceived = { transferData, index ->
                        onDragAndDropEventReceived(transferData, index)
                    },
                    isError = isGuessWrong
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    icon = icons[1],
                    onClick = { onIconClick("$WORDS_AUDIO${wordsId[1]}") },
                )
                Spacer(modifier = modifier.width(4.dp))
                ReceivingContainer(
                    title = currentWords[1] ?: "",
                    index = 1,
                    onDragAndDropEventReceived = { transferData, index ->
                        onDragAndDropEventReceived(transferData, index)
                    },
                    isError = isGuessWrong,
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    icon = icons[2],
                    onClick = { onIconClick("$WORDS_AUDIO${wordsId[2]}") },
                )
                Spacer(modifier = modifier.width(4.dp))
                ReceivingContainer(
                    title = currentWords[2] ?: "",
                    index = 2,
                    onDragAndDropEventReceived = { transferData, index ->
                        onDragAndDropEventReceived(transferData, index)
                    },
                    isError = isGuessWrong,
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                shareWords.map { title ->
                    ShareText(
                        title = title ?: "",
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))
            SubmitButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onDone = onDone,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReceivingContainer(
    title: String?,
    modifier: Modifier = Modifier,
    index: Int,
    isError: Boolean,
    onDragAndDropEventReceived: (DragAndDropEvent, Int) -> Unit,
) {
    val callback = remember {
        object : DragAndDropTarget {
            override fun onDrop(event: DragAndDropEvent): Boolean {
                onDragAndDropEventReceived(event, index)
                return true
            }
        }
    }
    Box(
        modifier = modifier
            .clip(ShapeDefaults.Small)
            .border(
                width = 2.dp,
                color = if (isError) colorError else colorProgressBar,
                shape = ShapeDefaults.Small
            )
            .background(colorProgressBar)
            .padding(horizontal = 3.dp)
            .size(150.dp)
            .dragAndDropTarget(
                shouldStartDragAndDrop = { startEvent: DragAndDropEvent ->
                    startEvent
                        .mimeTypes()
                        .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = callback
            ),
        contentAlignment = Alignment.Center
    ) {
        title?.let {
            AutoSizeText(
                text = it,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.displayMedium,
                minFontSize = 32.sp,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShareText(
    modifier: Modifier = Modifier,
    title: String,
) {
    val currentTitle by rememberUpdatedState(title)
    Box(
        modifier = modifier
            .wrapContentSize()
            .dragAndDropSource {
                detectTapGestures(
                    onPress = {
                        startTransfer(
                            DragAndDropTransferData(
                                clipData = ClipData.newPlainText("text", currentTitle)
                            )
                        )
                    }
                )
            },
        contentAlignment = Alignment.Center
    )
    {
        AutoSizeText(
            text = currentTitle,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            textAlign = TextAlign.Center,
            minFontSize = 22.sp,
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
            .size(150.dp)
            .clip(ShapeDefaults.Small)
            .background(colorProgressBar),
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
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            onDone()
        },
        modifier = modifier
            .wrapContentSize()
            .padding(bottom = 16.dp),
        colors = ButtonColors(
            colorProgressBar,
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

@Preview(widthDp = 410, heightDp = 700)
@Composable
private fun ThirdTaskPreview() {
    NivkhAlphabetComposeTheme {
        ThirdTaskLayout(
            titles = listOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            wordsId = listOf("1.2", "1.3", "1.1"),
            icons = listOf(null, null, null),
            onIconClick = {},
            onDone = {},
            onDragAndDropEventReceived = { _, _ -> },
            shareWords = arrayListOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            currentWords = arrayListOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            isGuessWrong = false,
        )
    }
}