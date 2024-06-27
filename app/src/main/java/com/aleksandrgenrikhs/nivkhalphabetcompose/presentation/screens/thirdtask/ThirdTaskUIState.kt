package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.thirdtask

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val shuffledWord: List<Char> = emptyList(),
    val userInput: List<Char> = emptyList(),
    val icon: String? = "",
    val isCompleted: Boolean = false
)