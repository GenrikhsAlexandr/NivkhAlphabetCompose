package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class FourthTaskUIState(
    val selectedLetter: String = "",
    val wordId: String = "",
    val title: String = "",
    val icon: String = "",
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val isNetworkConnected: Boolean,
    val isGuessWrong: Boolean = false,
    val userGuess: String = "",
    val isClickable: Boolean = false
)