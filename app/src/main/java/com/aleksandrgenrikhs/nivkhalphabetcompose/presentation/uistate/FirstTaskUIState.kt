package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class FirstTaskUIState(
    val selectedLetter: String = "",
    val titles: List<String> = emptyList(),
    val wordsId: List<String> = emptyList(),
    val icons: List<String?> = emptyList(),
    val progressWords: List<Int> = emptyList(),
    val isCompletedWords: List<Boolean> = emptyList(),
    val isClickableWords: List<Boolean> = emptyList(),
    val isPlaying: Boolean = false,
    val progressLetter: Int = 0,
    val isClickableLetter: Boolean = true,
    val isCompletedLetter: Boolean = false,
    val isVisibleWord: Boolean = false,
    val isFinishAudio: Boolean = false,
)