package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens.thirdtask.ThirdTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThirdTaskViewModel
@Inject constructor(val interactor: AlphabetInteractor) : ViewModel() {

    private val _uiState: MutableStateFlow<ThirdTaskUIState> = MutableStateFlow(ThirdTaskUIState())
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
        interactor.taskCompleted(Task.THIRD.stableId, uiState.value.selectedLetter)
    */

}