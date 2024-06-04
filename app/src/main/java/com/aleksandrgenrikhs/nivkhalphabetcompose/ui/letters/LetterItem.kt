package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.letters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.shapes

@Composable
fun LetterItem(
    letter: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .size(100.dp)
            .border(width = 1.dp, color = colorProgressBar, shape = shapes.small)
            .clickable(onClick = onClick),
        shape = shapes.small
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorCardLetterItem),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = letter,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Preview(widthDp = 100, heightDp = 100)
@Composable
fun LetterElementPreview() {
    NivkhAlphabetComposeTheme {
        LetterItem(
            letter = Letters.A.title,
            onClick = {},
        )
    }
}