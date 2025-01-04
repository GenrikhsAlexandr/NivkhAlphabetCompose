package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskUIState(
    val titles: List<Int>,
    val stablesId: List<Int>,
    val icons: List<Int>,
    val routes: List<String>,
    val isTaskCompleted: List<Boolean> = listOf(false, false, false, false),
    val isNextTaskVisible: List<Boolean> = listOf(true, false, false, false),
)
