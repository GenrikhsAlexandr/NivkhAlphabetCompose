package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateLettersMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.LettersUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LettersViewModel
@Inject constructor(
    private val alphabetInteractor: AlphabetInteractor,
    private val uiStateMapper: UIStateLettersMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<LettersUIState> = MutableStateFlow(LettersUIState())
    val uiState = _uiState.asStateFlow()

    fun updateLetters() {
        val letters = Letters.entries
        val mappedLetters = uiStateMapper.map(letters)
        _uiState.update { state ->
            state.copy(
                letters = mappedLetters.letters,
                isLetterCompleted = mappedLetters.isLetterCompleted
            )
        }
    }

    suspend fun checkLetterCompletion() {
        val lettersCompleted = alphabetInteractor.getLetterCompleted(Task.FOURTH.stableId)?.map {
            it.title
        }
        if (lettersCompleted != null) {
            _uiState.update { state ->
                val newIsLetterCompleted = state.isLetterCompleted.toMutableList()
                state.letters.mapIndexed { index, letter ->
                    if (lettersCompleted.contains(letter)) {
                        newIsLetterCompleted[index] = true
                    }
                }
                state.copy(
                    isLetterCompleted = newIsLetterCompleted,
                )
            }
        }
    }
}