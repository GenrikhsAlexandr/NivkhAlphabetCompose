package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
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
    val mapper: UIStateRevisionSecondMapper,
    private val mediaPlayer: MediaPlayerInteractor,
    private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionSecondUIState> =
        MutableStateFlow(RevisionSecondUIState())
    val uiState = _uiState.asStateFlow()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    suspend fun getWords() {
        _uiState.update { state ->
            isLoading.value = true
            val listWords = mapper.map(interactor.getWordsForRevisionSecond())
            with(listWords) {
                state.copy(
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
            if (isCorrectAnswer && !isCompleted) {
                viewModelScope.launch {
                    if (!isLoading.value) {
                        delay(1000)
                        getWords()
                    }
                }
            }
            state.copy(
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
        mediaPlayer.playerDestroy()
        mediaPlayer.initPlayer(context, url)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.playerDestroy()
    }
}