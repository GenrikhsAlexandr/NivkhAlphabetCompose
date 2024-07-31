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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
    wordsId: List<String>,
    icons: List<String?>,
    shareWords: List<String?>,
    currentWords: List<String?>,
    onIconClick: (String) -> Unit,
    isGuessWrong: Boolean,
    onDone: () -> Unit,
    onReset: () -> Unit,
    onDragAndDropEventReceived: (DragAndDropEvent, Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        if (wordsId.isNotEmpty()) {
            itemsIndexed(wordsId) { index, _ ->
                ReceivingContainerItem(
                    title = currentWords[index] ?: "",
                    index = index,
                    icon = icons[index] ?: "",
                    onClick = { onIconClick("$WORDS_AUDIO${wordsId[index]}") },
                    onDragAndDropEventReceived = { transferData, indexes ->
                        onDragAndDropEventReceived(transferData, indexes)
                    },
                    isError = isGuessWrong
                )
            }
        }
        item {
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
            Spacer(modifier = modifier.height(16.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ResetButton(
                    onReset = onReset,
                )
                Spacer(modifier = modifier.width(8.dp))
                SubmitButton(
                    onDone = onDone,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReceivingContainerItem(
    modifier: Modifier = Modifier,
    title: String,
    index: Int,
    icon: String,
    onClick: () -> Unit,
    onDragAndDropEventReceived: (DragAndDropEvent, Int) -> Unit,
    isError: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            icon = icon,
            onClick = onClick,
        )
        Spacer(modifier = modifier.width(4.dp))
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
            AutoSizeText(
                text = title,
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
        AsyncImage(
            model = icon,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun ResetButton(
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            onReset()
        },
        modifier = modifier
            .wrapContentSize(),
        colors = ButtonColors(
            colorError,
            colorRight,
            colorRight,
            colorRight
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.reset),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
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
            wordsId = listOf("1.2", "1.3", "1.1"),
            icons = listOf(null, null, null),
            onIconClick = {},
            onDone = {},
            onReset = {},
            onDragAndDropEventReceived = { _, _ -> },
            shareWords = arrayListOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            currentWords = arrayListOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            isGuessWrong = false,
        )
    }
}