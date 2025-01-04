package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class RevisionChooseRightWordUIState(
    val correctWordId: String = "",
    val wordsId: List<String> = emptyList(),
    val title: List<String> = emptyList(),
    val icon: String? = null,
    val isCorrectAnswers: List<Boolean?> = emptyList(),
    val isUserAnswerCorrect: Boolean = false,
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val shouldPlayFinishAudio: Boolean = false,
)
