package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class SecondTaskUIState(
    val selectedLetter: String = "",
    val letterId: List<String> = emptyList(),
    val title: List<String> = emptyList(),
    val wordId: List<String> = emptyList(),
    val icon: List<String?> = emptyList(),
    val isFlipped: List<Boolean> = emptyList(),
    val isCorrectAnswer: List<Boolean> = emptyList(),
    val correctAnswersCount: Int = 0,
    val isCorrectWord: Boolean = false,
    val isCompleted: Boolean = false,
    val isFinishAudio: Boolean = false,
)