package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar

@Composable
fun RevisionTaskLayout(
    @StringRes titleResId: List<Int>,
    @DrawableRes iconId: List<Int>,
    route: List<String>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        itemsIndexed(titleResId) { index, _ ->
            RevisionTaskItem(
                iconResId = iconId[index],
                onTaskClick = {
                    onClick(route[index])
                }
            )
        }
    }
}

@Composable
private fun RevisionTaskItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    onTaskClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .padding(8.dp)
            .clickable(onClick = onTaskClick)
            .clip(ShapeDefaults.Small)
            .background(colorProgressBar),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = modifier
                .fillMaxHeight()
                .padding(8.dp)
        )
    }
}

@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun RevisionTaskLayoutPreview() {
    NivkhAlphabetComposeTheme {
        RevisionTaskLayout(
            titleResId = listOf(
                R.string.revisionTaskFirst,
                R.string.revisionTaskSecond,
                R.string.revisionTaskThird
            ),
            iconId = listOf(
                R.drawable.ic_revision_task_first,
                R.drawable.ic_revision_task_second,
                R.drawable.ic_revision_task_third
            ),
            route = listOf(),
            onClick = { }
        )
    }
}