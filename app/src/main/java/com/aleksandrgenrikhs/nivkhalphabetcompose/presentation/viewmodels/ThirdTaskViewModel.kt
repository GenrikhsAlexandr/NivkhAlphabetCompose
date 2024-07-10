package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.ClipData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private var shareWords = mutableListOf<String>()
    suspend fun getWords(letterId: String) {
        val listWords = interactor.getWordsForThirdTask(letterId)
        shareWords = mutableListOf(*listWords.shuffled().map { it.title }.toTypedArray())
        if (listWords.isNotEmpty()) {
            _uiState.update {
                _uiState.value.copy(
                    words = listWords,
                    shareWords = shareWords
                )
            }
        }
    }

    fun playSound(wordId: String) {
        interactor.playerDestroy()
        interactor.initPlayer("${WORDS_AUDIO}$wordId")
        interactor.play()
    }

    fun updateReceivingContainer(clipData: ClipData?, index: Int) {
        if (clipData == null || clipData.itemCount == 0) return
        val sharedText = clipData.getItemAt(0).text.toString()
        if (shareWords.contains(sharedText)) {
            shareWords.remove(sharedText)
        }
        _uiState.update {
            val newCurrentWords = it.currentWords.toMutableList()
            newCurrentWords[index] = sharedText
            it.copy(currentWords = newCurrentWords)
        }
    }

    fun checkMatching() {
        val currentWords = uiState.value.currentWords
        val correctWords = uiState.value.words.map { it.title }
        if (currentWords == correctWords) {
            _uiState.update { it.copy(isAnswerCorrect = true) }
        } else {
            viewModelScope.launch {
                getWords(uiState.value.selectedLetter)
                _uiState.update {
                    it.copy(
                        currentWords = mutableListOf(null, null, null),
                        isAnswerCorrect = false,
                        shareWords = shareWords
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}