package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val shuffledWord: List<Char> = emptyList(),
    val userInput: List<Char> = emptyList(),
    val icon: String? = "",
    val isCompleted: Boolean = true,
    val isNetworkConnected: Boolean
)