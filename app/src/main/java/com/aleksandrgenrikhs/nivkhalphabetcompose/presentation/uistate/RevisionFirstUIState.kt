package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class RevisionFirstUIState(
    val letters: List<String> = emptyList(),
    val correctLetter: String = "",
    val isCorrectAnswer: List<Boolean?> = emptyList(),
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val isUserAnswerCorrect: Boolean = false,
    val isFinishAudio: Boolean = false,
)