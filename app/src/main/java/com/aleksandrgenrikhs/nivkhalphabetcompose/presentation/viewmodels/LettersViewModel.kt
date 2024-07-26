package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.LettersUIState
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

    private val _uiState: MutableStateFlow<LettersUIState> = MutableStateFlow(LettersUIState(
        letters = Letters.entries.map { it.title },
        isLetterCompleted = Letters.entries.map { it.isCompleted }
    ))
    val uiState = _uiState.asStateFlow()

    suspend fun isLetterCompleted() {
        val lettersCompleted = interactor.getLetterCompleted(Task.FOURTH.stableId)?.map {
            it.title
        }
        if (lettersCompleted != null) {
            _uiState.update { uiState ->
                val newIsLetterCompleted = uiState.isLetterCompleted.toMutableList()
                uiState.letters.mapIndexed { index, letter ->
                    if (lettersCompleted.contains(letter)) {
                        newIsLetterCompleted[index] = true
                    }
                }
                uiState.copy(
                    isLetterCompleted = newIsLetterCompleted,
                )
            }
        }
    }
}