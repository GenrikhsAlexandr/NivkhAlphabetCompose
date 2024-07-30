package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class RevisionSecondUIState(
    val correctWordId: String = "",
    val wordsId: List<String> = emptyList(),
    val words: List<String> = emptyList(),
    val icon: String? = null,
    val isCorrectAnswers: List<Boolean?> = emptyList(),
    val isUserAnswerCorrect: Boolean = false,
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val isFinishAudio: Boolean = false,
)