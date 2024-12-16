package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.AppBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarAbout,
                    navigation = { navController.popBackStack() }
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
                .background(colorPrimary)
                .padding(horizontal = 8.dp),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        )
        {
            item {

                Text(
                    text = stringResource(id = R.string.aboutText),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = stringResource(id = R.string.aboutSubText),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(shape = CircleShape),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sakhalin_energy),
                        contentDescription = null,
                        modifier = Modifier.clip(shape = CircleShape)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_ncbs),
                        contentDescription = null,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_dominanta),
                        contentDescription = null,
                        modifier = Modifier.clip(MaterialTheme.shapes.medium)
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 810)
@Composable
private fun AboutScreenPreview() {
    NivkhAlphabetComposeTheme {
        AboutScreen(
            navController = rememberNavController()
        )
    }
}