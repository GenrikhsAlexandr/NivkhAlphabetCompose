package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel

data class SecondTaskUIState(
    val selectedLetter: String = "",
    val words: List<SecondTaskModel> = emptyList(),
    val correctAnswersCount: Int = 0,
    val isAnswerCorrect: Boolean = false,
    val isCompleted: Boolean = false,
    val isNetworkConnected: Boolean
)