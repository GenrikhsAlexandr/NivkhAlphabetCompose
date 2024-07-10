package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val words: List<ThirdTaskModel> = emptyList(),
    val shareWords: MutableList<String> = mutableListOf(),
    val isAnswerCorrect: Boolean = false,
    val isNetworkConnected: Boolean,
    val currentWords: MutableList<String?> = mutableListOf(null, null, null),
)