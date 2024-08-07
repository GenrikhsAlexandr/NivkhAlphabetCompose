package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.RevisionFirstInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionFirstMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionFirstUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTER_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RevisionFirstViewModel
@Inject constructor(
    private val revisionFirstInteractor: RevisionFirstInteractor,
    private val mapper: UIStateRevisionFirstMapper,
    private val mediaPlayer: MediaPlayerInteractor,
    private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionFirstUIState> =
        MutableStateFlow(RevisionFirstUIState())
    val uiState = _uiState.asStateFlow()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun getLetters() {
        _uiState.update { state ->
            isLoading.value = true
            val allLetters = revisionFirstInteractor.getLettersForRevisionFirst()
            val mappedLetters = mapper.map(allLetters)
            playSound("$LETTER_AUDIO${mappedLetters.correctLetter}")
            with(mappedLetters) {
                state.copy(
                    letters = letters,
                    isCorrectAnswers = isCorrectAnswers,
                    correctLetter = correctLetter,
                    isUserAnswerCorrect = false
                )
            }
        }
        isLoading.value = false
    }

    fun checkUserGuess(letter: String) {
        _uiState.update { state ->
            val isCorrectAnswer = letter == state.correctLetter
            val correctAnswersCount = if (isCorrectAnswer) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 5
            val index = state.letters.indexOfFirst { it == letter }
            val newIsCorrectAnswerList = state.isCorrectAnswers.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectAnswer
            if (isCorrectAnswer) {
                playSound("$LETTER_AUDIO$letter")
            } else {
                playSound(ERROR_AUDIO)
            }
            if (isCorrectAnswer && !isCompleted) {
                viewModelScope.launch {
                    if (!isLoading.value) {
                        delay(1000)
                        getLetters()
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