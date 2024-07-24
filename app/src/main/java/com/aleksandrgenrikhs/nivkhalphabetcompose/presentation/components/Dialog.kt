package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar

@Composable
fun Dialog(
    title: String,
    textButtonBack: String,
    textButtonNext: String,
    navigationBack: () -> Unit,
    navigationNext: () -> Unit,
    painter: AsyncImagePainter,
    isVisibleSecondButton: Boolean,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(ShapeDefaults.Medium)
                .background(colorProgressBar)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (isVisibleSecondButton) {
                        TextButton(
                            onClick = {
                                navigationBack()
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = textButtonBack,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                        TextButton(
                            onClick = {
                                navigationNext()
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = textButtonNext,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Button(
                            colors = ButtonColors(
                                containerColor = colorPrimary,
                                contentColor = colorOnPrimary,
                                disabledContainerColor = colorPrimary,
                                disabledContentColor = colorOnPrimary
                            ),
                            onClick = {
                                navigationNext()
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = textButtonNext,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}