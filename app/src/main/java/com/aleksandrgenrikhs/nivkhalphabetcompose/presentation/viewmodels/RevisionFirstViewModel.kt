package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
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
    val interactor: AlphabetInteractor,
    val mapper: UIStateRevisionFirstMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionFirstUIState> =
        MutableStateFlow(RevisionFirstUIState())
    val uiState = _uiState.asStateFlow()

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    suspend fun getLetters() {
        _uiState.update { state ->
            isLoading.value = true
            val listLetters = mapper.map(interactor.getLettersForRevisionFirst())
            playSound("$LETTER_AUDIO${listLetters.correctLetter}")
            with(listLetters) {
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
        interactor.playerDestroy()
        interactor.initPlayer(url)
        interactor.play()
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}