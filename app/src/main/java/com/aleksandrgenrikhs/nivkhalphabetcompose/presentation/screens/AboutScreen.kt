package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.LazyListScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ShowDividerWhenScrolled

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onDividerVisibilityChange: (Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    val scrollableState: ScrollableState = LazyListScrollableState(listState)

    ShowDividerWhenScrolled(onDividerVisibilityChange, scrollableState)

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    )
    {
        item {
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.about_text),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(widthDp = 410, heightDp = 610)
@Composable
private fun AboutScreenPreview() {
    NivkhAlphabetComposeTheme {
        AboutScreen(
            onDividerVisibilityChange = {}
        )
    }
}