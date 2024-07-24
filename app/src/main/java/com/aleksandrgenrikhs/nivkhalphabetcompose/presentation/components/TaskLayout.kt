package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.sp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.shapes

@Composable
fun TaskLayout(
    @StringRes titleResId: List<Int>,
    @DrawableRes iconId: List<Int>,
    isTaskCompleted: List<Boolean>,
    isNextTaskVisible: List<Boolean>,
    route: List<String>,
    letter: String,
    modifier: Modifier = Modifier,
    onClick: (String, String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ic_start_alphabet),
                contentDescription = null,
                modifier = modifier
                    .size(200.dp),
                alignment = Alignment.Center
            )
        }
        if (titleResId.isNotEmpty()) {
            itemsIndexed(titleResId) { index, task ->
                TaskItem(
                    task = task,
                    iconResId = iconId[index],
                    onTaskClick = {
                        if (isNextTaskVisible[index]) {
                            onClick(route[index], letter)
                        }
                    },
                    isClickable = isNextTaskVisible[index],
                    isCompleted = isTaskCompleted[index]
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    modifier: Modifier = Modifier,
    @StringRes task: Int,
    @DrawableRes iconResId: Int,
    onTaskClick: () -> Unit,
    isClickable: Boolean,
    isCompleted: Boolean
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .clickable(onClick = onTaskClick),
        shape = shapes.small
    ) {
        Box(
            modifier = modifier
                .background(colorOnPrimary)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
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
                        .fillMaxHeight()
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    fontSize = 32.sp,
                    text = stringResource(task),
                    color = if (isCompleted) colorProgressBar else colorText,
                    style = MaterialTheme.typography.displayMedium,
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

@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun TaskItemPreview() {
    NivkhAlphabetComposeTheme {
        TaskLayout(
            titleResId = listOf(R.string.firstTask, R.string.secondTask, R.string.thirdTask),
            iconId = listOf(R.drawable.ic_task1, R.drawable.ic_task2, R.drawable.ic_task3),
            route = listOf(),
            isTaskCompleted = listOf(true, false, false),
            isNextTaskVisible = listOf(true, true, false),
            letter = "Aa",
            onClick = { _, _ -> }
        )
    }
}