package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.secondtask

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel

data class SecondTaskUIState(
    val selectedLetter: String = "",
    val words: List<WordModel> = emptyList(),
)