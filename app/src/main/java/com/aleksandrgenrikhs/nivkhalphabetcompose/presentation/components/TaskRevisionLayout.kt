package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.LazyListScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ShowDividerWhenScrolled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRevisionLayout(
    @StringRes titles: List<Int>,
    @DrawableRes icons: List<Int>,
    routes: List<String>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    onBack: () -> Unit
) {
    var isDividerVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scrollableState: ScrollableState = LazyListScrollableState(listState)
    val savedScrollPosition = rememberSaveable { listState.firstVisibleItemScrollOffset }
    ShowDividerWhenScrolled(
        onDividerVisibilityChange = { isVisible ->
            isDividerVisible = isVisible
        },
        scrollableState = scrollableState
    )
// Восстанавливаем позицию прокрутки при загрузке экрана
    LaunchedEffect(Unit) {
        listState.scrollToItem(savedScrollPosition)
    }

    val action: @Composable RowScope.() -> Unit = {
        DialogInfo(title = stringResource(R.string.infoRevisionTasksScreen))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarTask(
                        title = stringResource(id = R.string.revisionTask),
                        actions = action,

                        ),
                    navigation = onBack
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(colorPrimary),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(titles) { index, _ ->
                RevisionTaskItem(
                    iconResId = icons[index],
                    onTaskClick = {
                        onClick(routes[index])
                    },
                    title = titles[index]
                )
            }
        }
    }
}

@Composable
private fun RevisionTaskItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    onTaskClick: () -> Unit,
    @StringRes title: Int
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .wrapContentWidth()
            .padding(horizontal = 16.dp)
            .clip(ShapeDefaults.Medium)
            .clickable(
                onClick = onTaskClick,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(130.dp)
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = stringResource(id = title),
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}


@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun TaskRevisionLayoutPreview() {
    NivkhAlphabetComposeTheme {
        TaskRevisionLayout(
            titles = listOf(
                R.string.revisionFirst,
                R.string.revisionSecond,
                R.string.revisionThird
            ),
            icons = listOf(
                R.drawable.ic_revision_task_first,
                R.drawable.ic_revision_task_second,
                R.drawable.ic_revision_task_third
            ),
            routes = listOf(),
            onClick = { },
            onBack = {}
        )
    }
}