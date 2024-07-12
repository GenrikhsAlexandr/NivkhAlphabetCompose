package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel

data class ThirdTaskUIState(
    val selectedLetter: String = "",
    val words: List<ThirdTaskModel> = emptyList(),
    val shareWords: List<String?> = listOf(null, null, null),
    val isAnswerCorrect: Boolean = false,
    val isNetworkConnected: Boolean,
    val currentWords: List<String?> = listOf(null, null, null),
    val isFinishAudio: Boolean = false,
    val isGuessWrong: Boolean = false,
)