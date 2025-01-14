package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class LettersUIState(
    val letters: List<String> = emptyList(),
    val isLetterCompleted: List<Boolean> = emptyList(),
    val isCertificateCreated: Boolean = false,
    val isAllLettersCompleted: Boolean = false,
    val timeLearning: Int = 0,
)
