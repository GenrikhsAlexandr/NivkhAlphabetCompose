package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.letters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NavConstants.TASKS_SCREEN


@Composable
fun LetterScreen(
    modifier: Modifier,
    navController: NavController,
    lettersViewModel: LettersViewModel = viewModel()
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(colorPrimary)
            .padding(
                start = 8.dp,
                end = 8.dp,
            ),
        contentPadding = PaddingValues(vertical = 8.dp),
        columns = GridCells.Adaptive(100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(lettersViewModel.letters.value) { letter ->
            LetterItem(letter = letter.title,
                onClick = {
                    navController.navigate("${TASKS_SCREEN}/${letter.title}")
                })
        }
    }
}