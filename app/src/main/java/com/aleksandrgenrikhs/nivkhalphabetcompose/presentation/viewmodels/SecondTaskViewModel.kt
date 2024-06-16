package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.secondtask.SecondTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SecondTaskViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SecondTaskUIState> = MutableStateFlow(SecondTaskUIState())
    val uiState = _uiState.asStateFlow()

    fun setLetter(letter: String) {
        _uiState.value = _uiState.value.copy(selectedLetter = letter)
    }

    suspend fun getWords(letterId: String) {
        val listWords = interactor.getWords(letterId)
        if (listWords.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                words = listWords,
            )
        }
    }
/*
    interactor.taskCompleted(Task.SECOND.stableId, uiState.value.selectedLetter)
*/
}