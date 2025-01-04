package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class TaskLearnLetterUIState(
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
    val showDialog: Boolean = false,
    val shouldPlayFinishAudio: Boolean = false,
)
