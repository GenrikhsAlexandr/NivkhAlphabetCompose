package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class FirstRevisionUIState(
    val letters: List<String> = emptyList(),
    val correctLetter: String = "",
    val isCorrectAnswers: List<Boolean?> = emptyList(),
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val isUserAnswerCorrect: Boolean = false,
    val shouldPlayFinishAudio: Boolean = false,
)