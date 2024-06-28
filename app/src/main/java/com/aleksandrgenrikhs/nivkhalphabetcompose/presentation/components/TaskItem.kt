package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.shapes

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    @StringRes task: Int,
    @DrawableRes iconResId: Int,
    onTaskClick: () -> Unit,
    isClickable: Boolean,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .border(width = 1.dp, color = colorProgressBar, shape = shapes.small)
            .clickable(onClick = onTaskClick),
        shape = shapes.small
    ) {
        Box(
            modifier = modifier
                .background(colorOnPrimary)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .background(colorCardLetterItem)

            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = modifier
                        .width(150.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = stringResource(task),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                )
            }
            if (!isClickable) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = colorText,
                    modifier = modifier
                        .align(
                            Alignment.TopEnd
                        )
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(widthDp = 600, heightDp = 100)
@Composable
fun TaskItemPreview() {
    NivkhAlphabetComposeTheme {
        TaskItem(
            task = R.string.firstTask,
            iconResId = R.drawable.ic_task1,
            onTaskClick = {},
            isClickable = false
        )
    }
}