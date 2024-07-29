package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionFirstMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionFirstUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
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
        _uiState.update { uiState ->
            isLoading.value = true
            val letters = interactor.getLettersForRevisionFirst()
            val newListLetters = mapper.map(letters)
            uiState.copy(
                letters = newListLetters.letters,
                isCorrectAnswer = newListLetters.isCorrectAnswer,
                correctLetter = newListLetters.correctLetter,
                isUserAnswerCorrect = false
            )
        }
        isLoading.value = false
    }

    fun checkUserGuess(letter: String) {
        _uiState.update { uiState ->
            val isCorrectAnswer = letter == uiState.correctLetter
            val correctAnswersCount = if (isCorrectAnswer) {
                uiState.correctAnswersCount + 1
            } else {
                uiState.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 5
            val index = uiState.letters.indexOfFirst { it == letter }
            val newIsCorrectAnswerList = uiState.isCorrectAnswer.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectAnswer
            if (isCorrectAnswer) {
                playSound("${Constants.LETTER_AUDIO}${letter}")
            } else {
                playSound(ERROR_AUDIO)
            }
            if (isCorrectAnswer && !isCompleted) {
                viewModelScope.launch {
                    if (!isLoading.value) {
                        delay(2000)
                        getLetters()
                    }
                }
            }
            uiState.copy(
                isCorrectAnswer = newIsCorrectAnswerList,
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
        interactor.clearPreviousWordsList()
        interactor.playerDestroy()
    }
}