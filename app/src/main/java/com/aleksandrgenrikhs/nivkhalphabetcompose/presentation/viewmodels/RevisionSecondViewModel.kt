package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionSecondMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionSecondUIState
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
class RevisionSecondViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
    val mapper: UIStateRevisionSecondMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionSecondUIState> =
        MutableStateFlow(RevisionSecondUIState())
    val uiState = _uiState.asStateFlow()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    suspend fun getWords() {
        _uiState.update { uiState ->
            isLoading.value = true
            val listWords = mapper.map(interactor.getWordsForRevisionSecond())
            with(listWords) {
                uiState.copy(
                    correctWordId = correctWordId,
                    wordsId = wordsId,
                    words = words,
                    icon = icon,
                    isCorrectAnswers = isCorrectAnswers,
                    isUserAnswerCorrect = false
                )
            }
        }
        isLoading.value = false
    }

    fun checkUserGuess(wordId: String) {
        _uiState.update { uiState ->
            val isCorrectAnswer = wordId == uiState.correctWordId
            val correctAnswersCount = if (isCorrectAnswer) {
                uiState.correctAnswersCount + 1
            } else {
                uiState.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 5
            val index = uiState.wordsId.indexOfFirst { it == wordId }
            val newIsCorrectAnswerList = uiState.isCorrectAnswers.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectAnswer
            if (isCorrectAnswer) {
                playSound("${WORDS_AUDIO}${wordId}")
            } else {
                playSound(ERROR_AUDIO)
            }
            if (isCorrectAnswer && !isCompleted) {
                viewModelScope.launch {
                    if (!isLoading.value) {
                        delay(1000)
                        getWords()
                    }
                }
            }
            uiState.copy(
                isCorrectAnswers = newIsCorrectAnswerList,
                isCompleted = isCompleted,
                isUserAnswerCorrect = isCorrectAnswer,
                correctAnswersCount = correctAnswersCount
            )
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
        interactor.playerDestroy()
    }
}