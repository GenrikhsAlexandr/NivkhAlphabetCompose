package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class RevisionFirstUIState(
    val letters: List<String> = emptyList(),
    val correctLetter: String = "",
    val isCorrectAnswer: List<Boolean?> = emptyList(),
    val isUserAnswerCorrect: Boolean = false,
    val isFinishAudio: Boolean = false,
)