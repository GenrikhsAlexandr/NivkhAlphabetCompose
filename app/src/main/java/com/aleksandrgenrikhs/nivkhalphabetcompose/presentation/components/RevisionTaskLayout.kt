package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun RevisionTaskLayout(
    @StringRes titles: List<Int>,
    @DrawableRes icons: List<Int>,
    routes: List<String>,
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

@Composable
private fun RevisionTaskItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    onTaskClick: () -> Unit,
    @StringRes title: Int
) {
    Column(
        modifier = Modifier
            .size(200.dp)
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
            modifier = Modifier
                .height(130.dp)
                .padding(8.dp)
        )
        Text(
            text = stringResource(id = title),
            modifier = modifier
                .padding(8.dp),
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun RevisionTaskLayoutPreview() {
    NivkhAlphabetComposeTheme {
        RevisionTaskLayout(
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
            onClick = { }
        )
    }
}