package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.letters

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters

class LettersViewModel : ViewModel() {

    private val _uiState = mutableStateOf(LettersUIState(
        letters = Letters.entries))
    val uiState get() = _uiState
}