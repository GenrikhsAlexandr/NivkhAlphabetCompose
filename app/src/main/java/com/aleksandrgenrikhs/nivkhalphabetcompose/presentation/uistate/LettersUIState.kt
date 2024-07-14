package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.LetterModel

data class LettersUIState(
    val letters: List<LetterModel>,
    val isVisibleRepeat: Boolean = false,
    val listLettersCompleted: List<Letters>? = null
)