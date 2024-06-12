package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.firsttask

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word

data class FirstTaskUIState(
    val selectedLetter: String = "",
    val words: List<Word> = emptyList(),
    val isPlaying: Boolean = false,
    val progressLetter: Int = 0,
    val isClickableLetter: Boolean = true,
    val isCompletedLetter: Boolean = false,
    val getWordError: Boolean = false,
    val isVisibleWord: Boolean = false,
    val isCompleted: Boolean = false,
)