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
import androidx.compose.foundation.lazy.items
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.TaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.shapes

@Composable
fun TaskLayout(
    task: List<TaskModel>,
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
        items(task) {
            TaskItem(
                task = it.task.titleResId,
                iconResId = it.task.icon,
                onTaskClick = {
                    if (it.isNextTaskVisible) {
                        onClick(it.task.route, letter)
                    }
                },
                isClickable = it.isNextTaskVisible
            )
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    @StringRes task: Int,
    @DrawableRes iconResId: Int,
    onTaskClick: () -> Unit,
    isClickable: Boolean,
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

@Preview(widthDp = 600, heightDp = 400)
@Composable
fun TaskItemPreview() {
    NivkhAlphabetComposeTheme {
        /*TaskLayout(
            task = listOf(
                TaskModel(
                    task = Task(
                        stableId = 1,
                        titleResId = "",
                        icon = 0,
                        route = ""
                    ),
                    isTaskCompleted = true,

                    isNextTaskVisible = true
                )
            ),
            letter = "",
            onClick = { _, _ -> }
        )*/
    }
}