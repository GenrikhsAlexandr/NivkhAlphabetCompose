package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.SecondRevisionUseCase
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateSecondRevisionMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondRevisionUIState
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
class SecondRevisionViewModel
@Inject constructor(
    private val secondRevisionUseCase: SecondRevisionUseCase,
    private val uiStateMapper: UIStateSecondRevisionMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<SecondRevisionUIState> =
        MutableStateFlow(SecondRevisionUIState())
    val uiState = _uiState.asStateFlow()

    suspend fun updateWords() {
        _uiState.update { state ->
            val words = secondRevisionUseCase.getWordsForRevisionSecond()
            val mappedWords = uiStateMapper.map(words)
            with(mappedWords) {
                state.copy(
                    correctWordId = correctWordId,
                    wordsId = wordsId,
                    title = title,
                    icon = icon,
                    isCorrectAnswers = isCorrectAnswers,
                    isUserAnswerCorrect = false
                )
            }
        }
    }

    fun checkUserGuess(wordId: String) {
        _uiState.update { state ->
            val isCorrectAnswer = wordId == state.correctWordId
            val correctAnswersCount = if (isCorrectAnswer) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 5
            val index = state.wordsId.indexOfFirst { it == wordId }
            val newIsCorrectAnswerList = state.isCorrectAnswers.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectAnswer
            if (isCorrectAnswer) {
                playSound("$WORDS_AUDIO$wordId")
            } else {
                playSound(ERROR_AUDIO)
            }
            state.copy(
                isCorrectAnswers = newIsCorrectAnswerList,
                isCompleted = isCompleted,
                isUserAnswerCorrect = isCorrectAnswer,
                correctAnswersCount = correctAnswersCount
            )
        }
        processCorrectGuess()
    }

    private fun processCorrectGuess() {
        if (uiState.value.isUserAnswerCorrect && !uiState.value.isCompleted) {
            viewModelScope.launch {
                delay(1000)
                updateWords()
            }
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
        mediaPlayerInteractor.playerDestroy()
        mediaPlayerInteractor.initPlayer(context, url)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerInteractor.playerDestroy()
    }
}