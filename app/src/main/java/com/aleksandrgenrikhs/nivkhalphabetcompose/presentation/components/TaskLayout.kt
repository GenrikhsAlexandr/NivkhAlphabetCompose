package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskLayout(
    modifier: Modifier = Modifier,
    viewSate: TaskUIState,
    letter: String,
    onClick: (String) -> Unit,
    onBack: () -> Unit,
) {
    var isDividerVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        var previousDividerVisible = isDividerVisible
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { offset ->
                val isCurrentlyVisible = offset > 0
                if (isCurrentlyVisible != previousDividerVisible) {
                    previousDividerVisible = isCurrentlyVisible
                    isDividerVisible = isCurrentlyVisible
                }
            }
    }

    val action: @Composable RowScope.() -> Unit = {
        DialogInfo(title = stringResource(R.string.infoTasksScreen, letter))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarTask(
                        title = letter,
                        actions = action,

                        ),
                    navigation = onBack
                )
                if (isDividerVisible) {
                    HorizontalDivider(
                        color = colorText
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_tasks),
                    contentDescription = null,
                    modifier = modifier
                        .size(200.dp),
                    alignment = Alignment.Center
                )
            }
            if (viewSate.titles.isNotEmpty()) {
                itemsIndexed(viewSate.titles) { index, task ->
                    TaskItem(
                        task = task,
                        iconResId = viewSate.icons[index],
                        onTaskClick = {
                            if (viewSate.isNextTaskVisible[index]) {
                                onClick(viewSate.routes[index])
                            }
                        },
                        isClickable = viewSate.isNextTaskVisible[index],
                        isCompleted = viewSate.isTaskCompleted[index]
                    )
                }
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
    isCompleted: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .clickable(
                enabled = isClickable,
                onClick = onTaskClick
            )
            .clip(ShapeDefaults.Small)
            .background(colorOnPrimary),
        contentAlignment = Alignment.CenterStart
    ) {
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
                color = colorText,
                style = MaterialTheme.typography.displayMedium,
                modifier = modifier
                    .padding(horizontal = 4.dp)
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
                        Alignment.BottomEnd
                    )
                    .padding(8.dp)
            )
        }
        if (isCompleted) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = colorProgressBar,
                modifier = modifier
                    .align(
                        Alignment.BottomEnd
                    )
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TaskItemPreview() {
    NivkhAlphabetComposeTheme {
        TaskLayout(
            viewSate = TaskUIState(
                titles = listOf(R.string.firstTask, R.string.secondTask, R.string.thirdTask),
                icons = listOf(R.drawable.ic_task1, R.drawable.ic_task2, R.drawable.ic_task3),
                routes = listOf(),
                isTaskCompleted = listOf(true, false, false),
                isNextTaskVisible = listOf(true, true, false),
                stablesId = listOf()
            ),
            letter = "Aa",
            onClick = {},
            onBack = {}
        )
    }
}
