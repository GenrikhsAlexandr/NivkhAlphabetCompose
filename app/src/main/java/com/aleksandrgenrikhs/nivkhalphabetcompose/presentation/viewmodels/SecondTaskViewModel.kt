package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateSecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
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
    val mapper: UIStateSecondTaskMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<SecondTaskUIState> =
        MutableStateFlow(SecondTaskUIState())
    val uiState = _uiState.asStateFlow()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun setLetter(letter: String) {
        _uiState.update { uiState ->
            uiState.copy(selectedLetter = letter)
        }
    }

    suspend fun getWords(letterId: String) {
        _uiState.update { uiState ->
            isLoading.value = true
            val listWords = interactor.getWordsForSecondTask(letterId)
            val newWordList = mapper.map(listWords)
            uiState.copy(
                letterId = newWordList.letterId,
                wordId = newWordList.wordId,
                icon = newWordList.icon,
                isFlipped = newWordList.isFlipped,
                title = newWordList.title,
                isCorrectAnswer = newWordList.isCorrectAnswer,
                isCorrectWord = false
            )
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
            val index = uiState.wordId.indexOfFirst { it == wordId }
            val newIsFlippedList = uiState.isFlipped.toMutableList()
            newIsFlippedList[index] = !newIsFlippedList[index]
            val newIsCorrectAnswerList = uiState.isCorrectAnswer.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectWord

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
                isFlipped = newIsFlippedList,
                isCorrectAnswer = newIsCorrectAnswerList,
                correctAnswersCount = correctAnswersCount,
                isCompleted = isCompleted,
                isCorrectWord = isCorrectWord,
            )
        }
        if (uiState.value.isCompleted) {
            interactor.taskCompleted(Task.SECOND.stableId, uiState.value.selectedLetter)
        }
    }

    fun playSound(url: String) {
        if (url == FINISH_AUDIO) {
            _uiState.update { state ->
                state.copy(
                    isFinishAudio = true
                )
            }
        }
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