package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.thirdtask

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val words: List<WordModel> = emptyList(),
    )