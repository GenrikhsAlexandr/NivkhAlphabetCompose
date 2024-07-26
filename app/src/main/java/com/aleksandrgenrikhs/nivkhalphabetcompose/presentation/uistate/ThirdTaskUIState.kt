package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val title: List<String> = emptyList(),
    val wordId: List<String> = emptyList(),
    val icon: List<String?> = emptyList(),
    val shareWords: List<String?> = listOf(null, null, null),
    val isAnswerCorrect: Boolean = false,
    val currentWords: List<String?> = listOf(null, null, null),
    val isFinishAudio: Boolean = false,
    val isGuessWrong: Boolean = false,
)