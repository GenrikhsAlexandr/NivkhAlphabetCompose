package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val words: List<ThirdTaskModel> = emptyList(),
    val isAnswerCorrect: Boolean = false,
    val isNetworkConnected: Boolean
)