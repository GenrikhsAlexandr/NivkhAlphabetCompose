package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class LettersUIState(
    val letters: List<String> = emptyList(),
    val isLetterCompleted: List<Boolean> = emptyList(),
    val nameUser: String = "",
    val isUserNameNotEmpty: Boolean = false,
    val isAllLettersCompleted: Boolean = false
)