package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar

@Composable
fun DialogInfo(
    modifier: Modifier = Modifier,
    title: String,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clip(CircleShape)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_info_appbar),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .height(30.dp)
        )
    }
    if (expanded) {
        DialogInfoItem(
            onDismissRequest = { expanded = false },
            title = title
        )
    }
}

@Composable
private fun DialogInfoItem(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(ShapeDefaults.Medium)
                .background(colorProgressBar)
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 32.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .height(160.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp
                    ),
                    modifier = modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = modifier
                        .padding(8.dp)
                        .background(colorPrimary, CircleShape)
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 300, heightDp = 400)
@Composable
private fun DialogInfoPreview() {
    NivkhAlphabetComposeTheme {
        DialogInfoItem(
            title = stringResource(id = R.string.infoLettersScreen),
            onDismissRequest = {},

            )
    }
}