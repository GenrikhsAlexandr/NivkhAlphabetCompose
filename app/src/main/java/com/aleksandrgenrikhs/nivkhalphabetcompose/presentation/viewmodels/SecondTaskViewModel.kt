package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondTaskUIState
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
        MutableStateFlow(SecondTaskUIState())
    val uiState = _uiState.asStateFlow()

    fun setLetter(letter: String) {
        _uiState.value = _uiState.value.copy(selectedLetter = letter)
    }

    suspend fun getWords(letterId: String) {
        val listWords = interactor.getWordsForSecondTask(letterId)
        if (listWords.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                words = listWords.map { word ->
                    word.copy(
                        isFlipped = false
                    )
                },
                isAnswerCorrect = false

            )
        }
    }

    fun flipCard(wordId: String, letterId: String) {
        playSound(wordId)
        isPlaying()
        _uiState.update { uiState ->
            val isRightWord = letterId == uiState.selectedLetter
            val correctAnswersCount = if (isRightWord) {
                uiState.correctAnswersCount + 1
            } else {
                uiState.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 3

            val newWordsList = uiState.words.toMutableList().apply {
                val index = uiState.words.indexOfFirst { it.wordId == wordId }
                if (index != -1) {
                    this[index] = this[index].copy(
                        isFlipped = !this[index].isFlipped,
                        isRightAnswer = isRightWord
                    )
                    if (isRightWord && !isCompleted) {
                            viewModelScope.launch {
                                delay(2000)
                                getWords(letterId)
                            }
                        this.map {
                            it.copy(
                                isFlipped = false,
                            )
                        }
                    }
                }
            }
            uiState.copy(
                words = newWordsList,
                isCompleted = isCompleted,
                correctAnswersCount = correctAnswersCount,
                isAnswerCorrect = isRightWord,
            )
        }
        if (uiState.value.isCompleted) {
            interactor.clearPreviousWordsList()
            interactor.taskCompleted(Task.SECOND.stableId, uiState.value.selectedLetter)
        }
    }

    private fun playSound(wordId: String) {
        interactor.initPlayer("${UrlConstants.WORDS_AUDIO_FIRST_TASK}$wordId")
        interactor.play()
    }

    private fun isPlaying() {
        _uiState.update { uiState ->
            uiState.copy(
                isPlaying = true
            )
        }
        viewModelScope.launch {
            try {
                interactor.isPlaying().collect {
                    _uiState.update { uiState ->
                        uiState.copy(
                            isPlaying = it
                        )
                    }
                    if (!uiState.value.isPlaying) {
                        interactor.playerDestroy()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}