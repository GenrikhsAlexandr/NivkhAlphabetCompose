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
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun ThirdTaskLayout(
    modifier: Modifier = Modifier,
    words: List<ThirdTaskModel>,
    shareWords: List<String?>,
    currentWords: List<String?>,
    onIconClick: (String) -> Unit,
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
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                icon = words[0].icon,
                onClick = { onIconClick(words[0].wordId) },
            )
            ReceivingContainer(
                title = currentWords[0] ?: "",
                index = 0,
                onDragAndDropEventReceived = { transferData, index ->
                    onDragAndDropEventReceived(transferData, index)
                },
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
                icon = words[1].icon,
                onClick = { onIconClick(words[1].wordId) },
            )
            ReceivingContainer(
                title = currentWords[1] ?: "",
                onDragAndDropEventReceived = { transferData, index ->
                    onDragAndDropEventReceived(transferData, index)
                },
                index = 1
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
                icon = words[2].icon,
                onClick = { onIconClick(words[2].wordId) },
            )
            ReceivingContainer(
                title = currentWords[2] ?: "",
                onDragAndDropEventReceived = { transferData, index ->
                    onDragAndDropEventReceived(transferData, index)
                },
                index = 2
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
        Spacer(modifier = Modifier.weight(1f))
        SubmitButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onDone = onDone,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReceivingContainer(
    title: String?,
    modifier: Modifier = Modifier,
    index: Int,
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
            .border(
                width = 2.dp,
                color = colorProgressBar
            )
            .background(colorOnPrimary)
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
fun ShareText(
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
        Text(
            text = currentTitle,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(8.dp)
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
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            onDone()
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

@Preview(widthDp = 410, heightDp = 610)
@Composable
private fun ThirdTaskPreview() {
    NivkhAlphabetComposeTheme {
        ThirdTaskLayout(
            words = listOf(
                ThirdTaskModel(
                    title = "SAsna",
                    wordId = "",
                    icon = null,
                ),
                ThirdTaskModel(
                    title = "SAsna",
                    wordId = "",
                    icon = null,
                ),
                ThirdTaskModel(
                    title = "SAsna",
                    wordId = "",
                    icon = null,
                )
            ),
            onIconClick = {},
            onDone = {},
            onDragAndDropEventReceived = { _, _ -> },
            shareWords = arrayListOf(),
            currentWords = arrayListOf()

        )
    }
}