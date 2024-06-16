package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.firsttask

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel

data class FirstTaskUIState(
    val selectedLetter: String = "",
    val words: List<WordModel> = emptyList(),
    val isPlaying: Boolean = false,
    val progressLetter: Int = 0,
    val isClickableLetter: Boolean = true,
    val isCompletedLetter: Boolean = false,
    val getWordError: Boolean = false,
    val isVisibleWord: Boolean = false,
    val isCompleted: Boolean = false,
)