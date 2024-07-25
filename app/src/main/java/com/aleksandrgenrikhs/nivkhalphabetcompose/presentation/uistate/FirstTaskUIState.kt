package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class FirstTaskUIState(
    val selectedLetter: String = "",
    val title: List<String> = emptyList(),
    val wordId: List<String> = emptyList(),
    val icon: List<String?> = emptyList(),
    val progressWord: List<Int> = emptyList(),
    val isCompletedWord: List<Boolean> = emptyList(),
    val isClickableWord: List<Boolean> = emptyList(),
    val isPlaying: Boolean = false,
    val progressLetter: Int = 0,
    val isClickableLetter: Boolean = true,
    val isCompletedLetter: Boolean = false,
    val getWordError: Boolean = false,
    val isVisibleWord: Boolean = false,
    val isNetworkConnected: Boolean,
    val isFinishAudio: Boolean = false,
)