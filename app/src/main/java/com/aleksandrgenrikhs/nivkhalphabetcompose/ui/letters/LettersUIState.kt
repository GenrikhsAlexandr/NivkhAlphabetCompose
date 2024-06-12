package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.letters

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters

data class LettersUIState(
    val letters:List<Letters>,
    val completedLetters:List<String> = emptyList()
)