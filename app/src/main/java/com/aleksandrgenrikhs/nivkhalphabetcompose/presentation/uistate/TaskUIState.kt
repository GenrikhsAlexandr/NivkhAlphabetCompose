package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskUIState(
    val titleResId: List<Int>,
    val stableId: List<Int>,
    val iconId: List<Int>,
    val route: List<String>,
    val isTaskCompleted: List<Boolean> = listOf(false, false, false, false),
    val isNextTaskVisible: List<Boolean> = listOf(true, false, false, false),
)