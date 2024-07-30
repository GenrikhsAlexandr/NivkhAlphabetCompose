package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class RevisionThirdUIState(
    val titles: List<String> = emptyList(),
    val letters: List<String> = emptyList(),
    val icons: List<String?> = emptyList(),
    val shareWords: List<String?> = listOf(null, null),
    val shareLetters: List<String?> = listOf(null, null),
    val shareIcons: List<String?> = listOf(null, null),
    val isAnswerCorrect: Boolean = false,
    val currentWords: List<String?> = listOf(null, null),
    val currentLetters: List<String?> = listOf(null, null),
    val currentIcons: List<String?> = listOf(null, null),
    val correctWords: List<String?> = listOf(null, null),
    val correctLetters: List<String?> = listOf(null, null),
    val correctIcons: List<String?> = listOf(null, null),
    val isFinishAudio: Boolean = false,
    val isGuessWrong: Boolean = false,
)