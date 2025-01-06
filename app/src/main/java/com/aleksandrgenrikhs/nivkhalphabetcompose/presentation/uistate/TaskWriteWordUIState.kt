package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskWriteWordUIState(
    val selectedLetter: String = "",
    val wordId: String = "",
    val title: String = "",
    val icon: String = "",
    val correctAnswersCount: Int = 0,
    val isGuessWrong: Boolean = false,
    val inputWord: String = "",
    val isClickable: Boolean = false,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
)
