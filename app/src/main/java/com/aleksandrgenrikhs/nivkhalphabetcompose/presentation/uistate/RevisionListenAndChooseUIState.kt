package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class RevisionListenAndChooseUIState(
    val letters: List<String> = emptyList(),
    val correctLetter: String = "",
    val isCorrectAnswers: List<Boolean?> = emptyList(),
    val correctAnswersCount: Int = 0,
    val isUserAnswerCorrect: Boolean = false,
    val showDialog: Boolean = false,
)
