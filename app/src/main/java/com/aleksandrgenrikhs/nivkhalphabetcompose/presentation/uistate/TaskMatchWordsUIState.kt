package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskMatchWordsUIState(
    val selectedLetter: String = "",
    val titles: List<String> = emptyList(),
    val wordsId: List<String> = emptyList(),
    val icons: List<String?> = emptyList(),
    val shareableWords: List<String?> = listOf(null, null, null),
    val currentWords: List<String?> = listOf(null, null, null),
    val isGuessWrong: Boolean = false,
    val showDialog: Boolean = false,
)
