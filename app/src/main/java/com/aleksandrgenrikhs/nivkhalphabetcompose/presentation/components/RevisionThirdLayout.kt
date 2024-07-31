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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.idapgroup.autosizetext.AutoSizeText

@Composable
fun RevisionThirdLayout(
    modifier: Modifier = Modifier,
    title: String,
    letter: String,
    icon: String,
    shareWords: List<String?>,
    shareLetters: List<String?>,
    shareIcons: List<String?>,
    currentWords: List<String?>,
    currentLetters: List<String?>,
    currentIcons: List<String?>,
    isGuessWrong: Boolean,
    onDone: () -> Unit,
    onReset: () -> Unit,
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
        println("RevisionThirdLayout")
        if (title.isNotEmpty()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconItem(
                    icon = icon,
                )
                Spacer(modifier = modifier.width(4.dp))
                Column(
                    modifier = modifier
                        .clip(ShapeDefaults.Small)
                        .padding(horizontal = 3.dp)
                        .height(124.dp)
                        .wrapContentWidth(),
                ) {
                    ReceivingContainerItem(
                        title = currentLetters[0] ?: "",
                        icon = null,
                        index = 0,
                        onDragAndDropEventReceived = { transferData, index ->
                            onDragAndDropEventReceived(transferData, index)
                        },
                        isError = isGuessWrong
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    ReceivingContainerItem(
                        title = currentWords[0] ?: "",
                        icon = null,
                        index = 0,
                        onDragAndDropEventReceived = { transferData, index ->
                            onDragAndDropEventReceived(transferData, index)
                        },
                        isError = isGuessWrong
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ReceivingContainerItem(
                    title = null,
                    icon = currentIcons[0] ?: "",
                    index = 0,
                    onDragAndDropEventReceived = { transferData, index ->
                        onDragAndDropEventReceived(transferData, index)
                    },
                    isError = isGuessWrong,
                )
                Spacer(modifier = modifier.width(4.dp))
                Column(
                    modifier = modifier
                        .clip(ShapeDefaults.Small)
                        .padding(horizontal = 3.dp)
                        .height(124.dp)
                        .wrapContentWidth(),
                ) {
                    ReceivingContainerItem(
                        title = currentLetters[1] ?: "",
                        icon = null,
                        index = 1,
                        onDragAndDropEventReceived = { transferData, index ->
                            onDragAndDropEventReceived(transferData, index)
                        },
                        isError = isGuessWrong
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    TextItem(
                        title = title,
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ReceivingContainerItem(
                    title = null,
                    icon = currentIcons[1] ?: "",
                    index = 1,
                    onDragAndDropEventReceived = { transferData, index ->
                        onDragAndDropEventReceived(transferData, index)
                    },
                    isError = isGuessWrong,
                )
                Spacer(modifier = modifier.width(4.dp))
                Column(
                    modifier = modifier
                        .clip(ShapeDefaults.Small)
                        .padding(horizontal = 3.dp)
                        .height(124.dp)
                        .wrapContentWidth(),
                ) {
                    TextItem(
                        title = letter,
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    ReceivingContainerItem(
                        title = currentWords[1] ?: "",
                        icon = null,
                        index = 1,
                        onDragAndDropEventReceived = { transferData, index ->
                            onDragAndDropEventReceived(transferData, index)
                        },
                        isError = isGuessWrong
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                shareIcons.map { icon ->
                    ShareIcons(
                        icon = icon ?: ""
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                shareWords.map { title ->
                    ShareWords(
                        title = title ?: "",
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                shareLetters.map { letter ->
                    ShareLetters(
                        letter = letter ?: "",
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(8.dp))
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReceivingContainerItem(
    title: String?,
    icon: String?,
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
            .height(if (icon == null) 61.dp else 124.dp)
            .width(if (icon == null) 176.dp else 124.dp)
            .border(
                width = 2.dp,
                color = if (isError) colorError else colorProgressBar,
                shape = ShapeDefaults.Small
            )
            .background(colorProgressBar)
            .padding(horizontal = 3.dp)
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
        if (title != null) {
            AutoSizeText(
                text = title,
                maxLines = 1,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                minFontSize = 32.sp,
            )
        }
        if (icon != null) {
            AsyncImage(
                model = icon,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
private fun TextItem(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier
            .clip(ShapeDefaults.Small)
            .height(61.dp)
            .width(176.dp)
            .border(
                width = 2.dp,
                color = colorProgressBar,
                shape = ShapeDefaults.Small
            )
            .background(colorProgressBar)
            .padding(horizontal = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        AutoSizeText(
            textAlign = TextAlign.Center,
            text = title,
            maxLines = 1,
            style = MaterialTheme.typography.displayMedium,
            minFontSize = 32.sp,
        )
    }
}

@Composable
private fun IconItem(
    modifier: Modifier = Modifier,
    icon: String?,
) {
    Box(
        modifier = modifier
            .size(124.dp)
            .clip(ShapeDefaults.Small)
            .background(colorProgressBar),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShareWords(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .dragAndDropSource {
                detectTapGestures(
                    onPress = {
                        startTransfer(
                            DragAndDropTransferData(
                                clipData = ClipData.newPlainText("text", title)
                            )
                        )
                    }
                )
            },
        contentAlignment = Alignment.Center
    )
    {
        AutoSizeText(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            textAlign = TextAlign.Center,
            minFontSize = 22.sp,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShareLetters(
    modifier: Modifier = Modifier,
    letter: String,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .dragAndDropSource {
                detectTapGestures(
                    onPress = {
                        startTransfer(
                            DragAndDropTransferData(
                                clipData = ClipData.newPlainText("text", letter)
                            )
                        )
                    }
                )
            },
        contentAlignment = Alignment.Center
    )
    {
        AutoSizeText(
            text = letter,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            textAlign = TextAlign.Center,
            minFontSize = 22.sp,
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShareIcons(
    modifier: Modifier = Modifier,
    icon: String,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .dragAndDropSource {
                detectTapGestures(
                    onPress = {
                        startTransfer(
                            DragAndDropTransferData(
                                clipData = ClipData.newPlainText("image Url", icon)
                            )
                        )
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .size(50.dp)
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

@Preview(widthDp = 410, heightDp = 900)
@Composable
private fun ThirdTaskPreview() {
    NivkhAlphabetComposeTheme {
        RevisionThirdLayout(
            title = "ӈағзыр̆раӄ",
            letter = "Aa",
            icon = "null",
            onDone = {},
            onReset = {},
            onDragAndDropEventReceived = { _, _ -> },
            shareWords = arrayListOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            shareLetters = arrayListOf("Aa", "Bb", "Cc"),
            shareIcons = arrayListOf(null, null, null),
            currentWords = arrayListOf("ӈағзыр̆раӄ", "пʼиды пʼаӽ", "ӿатӽ пʼерӈ"),
            currentLetters = arrayListOf("Aa", "Bb", "Cc"),
            isGuessWrong = false,
            currentIcons = listOf(null, null, null)
        )
    }
}