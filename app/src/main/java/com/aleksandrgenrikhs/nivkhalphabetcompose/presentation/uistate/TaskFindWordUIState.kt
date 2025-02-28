package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskFindWordUIState(
    val selectedLetter: String = "",
    val lettersId: List<String> = emptyList(),
    val titles: List<String> = emptyList(),
    val wordsId: List<String> = emptyList(),
    val icons: List<String?> = emptyList(),
    val isFlipped: List<Boolean> = emptyList(),
    val isCorrectAnswers: List<Boolean> = emptyList(),
    val correctAnswersCount: Int = 0,
    val isCorrectWord: Boolean = false,
    val showDialog: Boolean = false,
)
