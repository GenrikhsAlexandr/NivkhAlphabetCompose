package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FifthTaskViewModel
@Inject constructor(val interactor: AlphabetInteractor) : ViewModel() {

    private val _uiState: MutableStateFlow<ThirdTaskUIState> = MutableStateFlow(ThirdTaskUIState())
    val uiState = _uiState.asStateFlow()

    fun setLetter(letter: String) {
        _uiState.value = _uiState.value.copy(selectedLetter = letter)
    }

    suspend fun getShuffledWord(letterId: String) {
        val word = interactor.getShuffledWord(letterId)
        _uiState.value = _uiState.value.copy(
            shuffledWord = word.title,
            icon = word.icon
        )
        interactor.taskCompleted(Task.FIFTH.stableId, uiState.value.selectedLetter)
    }
}