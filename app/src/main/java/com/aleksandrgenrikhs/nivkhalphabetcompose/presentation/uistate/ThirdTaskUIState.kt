package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val titles: List<String> = emptyList(),
    val wordsId: List<String> = emptyList(),
    val icons: List<String?> = emptyList(),
    val shareableWords: List<String?> = listOf(null, null, null),
    val isAnswerCorrect: Boolean = false,
    val currentWords: List<String?> = listOf(null, null, null),
    val shouldPlayFinishAudio: Boolean = false,
    val isGuessWrong: Boolean = false,
)