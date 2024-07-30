package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val titles: List<String> = emptyList(),
    val wordsId: List<String> = emptyList(),
    val icons: List<String?> = emptyList(),
    val shareWords: List<String?> = listOf(null, null, null),
    val isAnswerCorrect: Boolean = false,
    val currentWords: List<String?> = listOf(null, null, null),
    val isFinishAudio: Boolean = false,
    val isGuessWrong: Boolean = false,
)