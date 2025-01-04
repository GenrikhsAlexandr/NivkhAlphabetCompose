package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskWriteWordUIState(
    val selectedLetter: String = "",
    val wordId: String = "",
    val title: String = "",
    val icon: String = "",
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val isGuessWrong: Boolean = false,
    val userGuess: String = "",
    val isClickable: Boolean = false,
    val isLoading: Boolean = false,
    val shouldPlayFinishAudio: Boolean = false,
)
