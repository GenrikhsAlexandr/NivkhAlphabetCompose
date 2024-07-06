package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondTaskViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SecondTaskUIState> =
        MutableStateFlow(
            SecondTaskUIState(
                isNetworkConnected = interactor.isNetWorkConnected()
            )
        )
    val uiState = _uiState.asStateFlow()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun setLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(selectedLetter = letter)
        }
    }

    suspend fun getWords(letterId: String) {
        isLoading.value = true
        val listWords = interactor.getWordsForSecondTask(letterId)
        if (listWords.isNotEmpty()) {
            _uiState.update {
                _uiState.value.copy(
                    words = listWords.map { word ->
                        word.copy(
                            isFlipped = false
                        )
                    },
                    isAnswerCorrect = false
                )
            }
        }
        isLoading.value = false
    }

    fun flipCard(wordId: String, letterId: String) {
        _uiState.update { uiState ->
            val isCorrectWord = letterId == uiState.selectedLetter
            val correctAnswersCount = if (isCorrectWord) {
                uiState.correctAnswersCount + 1
            } else {
                uiState.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 3

            val newWordsList = uiState.words.toMutableList().apply {
                val index = uiState.words.indexOfFirst { it.wordId == wordId }
                this[index] = this[index].copy(
                    isFlipped = !this[index].isFlipped,
                    isCorrectAnswer = isCorrectWord
                )
            }

            if (isCorrectWord) {
                playSound("${WORDS_AUDIO}$wordId")
            } else {
                playSound(ERROR_AUDIO)
            }

            if (isCorrectWord && !isCompleted) {
                viewModelScope.launch {
                    if (!isLoading.value) {
                        delay(2000)
                        getWords(letterId)
                    }
                }
            }
            uiState.copy(
                words = newWordsList,
                isCompleted = isCompleted,
                correctAnswersCount = correctAnswersCount,
                isAnswerCorrect = isCorrectWord,
            )
        }
        if (uiState.value.isCompleted) {
            interactor.taskCompleted(Task.SECOND.stableId, uiState.value.selectedLetter)
        }
    }

    private fun playSound(url: String) {
        interactor.playerDestroy()
        interactor.initPlayer(url)
        interactor.play()
    }

    override fun onCleared() {
        super.onCleared()
        interactor.clearPreviousWordsList()
        interactor.playerDestroy()
    }
}