package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.LetterModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.letters.LettersUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LettersViewModel
@Inject constructor(
    private val interactor: AlphabetInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<LettersUIState> = MutableStateFlow(
        LettersUIState(
            letters = Letters.entries.map { letter ->
                LetterModel(
                    letter = letter,
                    isLetterCompleted = false
                )
            })
    )
    val uiState = _uiState.asStateFlow()

    fun isLetterCompleted() {
        val lettersCompleted = interactor.getLetterCompleted(Task.THIRD.stableId)
        if (lettersCompleted != null) {
            _uiState.update { uiState ->
                uiState.copy(
                    letters = uiState.letters.map { letter ->
                        letter.copy(
                            isLetterCompleted = lettersCompleted.contains(letter.letter)
                        )
                    }
                )
            }
        }
    }
}