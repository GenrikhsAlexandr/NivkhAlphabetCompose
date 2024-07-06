package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ThirdTaskViewModel
@Inject constructor(val interactor: AlphabetInteractor) : ViewModel() {

    private val _uiState: MutableStateFlow<ThirdTaskUIState> = MutableStateFlow(
        ThirdTaskUIState(
            isNetworkConnected = interactor.isNetWorkConnected()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun setLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(selectedLetter = letter)
        }
    }

    suspend fun getWords(letterId: String) {
        val listWords = interactor.getWordsForThirdTask(letterId)
        if (listWords.isNotEmpty()) {
            _uiState.update {
                _uiState.value.copy(
                    words = listWords,
                )
            }
        }
    }

    fun checkMatching(image: String, word: String) {
    }

    fun playSound(wordId: String) {
        interactor.playerDestroy()
        interactor.initPlayer("${WORDS_AUDIO}$wordId")
        interactor.play()
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}