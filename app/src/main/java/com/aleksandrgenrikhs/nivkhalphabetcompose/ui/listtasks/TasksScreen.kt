package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.listtasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.ui.theme.colorPrimary

@Composable
fun TasksScreen(
    modifier: Modifier,
    navController: NavController?,
    letter: String,
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentPadding = PaddingValues(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        items(
            tasksViewModel.tasks.value
        ) { task ->
            TaskItem(
                task = task.titleResId,
                iconResId = task.icon,
                onTaskClick = {
                    navController?.navigate("${task.route}/$letter")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview(
) {
    NivkhAlphabetComposeTheme {
        TasksScreen(
            modifier = Modifier,
            navController = null,
            letter = Letters.A.title,
        )
    }
}
