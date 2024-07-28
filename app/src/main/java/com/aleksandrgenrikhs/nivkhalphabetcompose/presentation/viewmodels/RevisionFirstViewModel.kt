package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionFirstUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RevisionFirstViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionFirstUIState> =
        MutableStateFlow(RevisionFirstUIState())
    val uiState = _uiState.asStateFlow()

    fun getLetters() {
        _uiState.update { uiState ->
            val newLettersList = Letters.entries.shuffled().take(4)
            uiState.copy(
                letters = newLettersList.map { it.title },
                isCorrectAnswer = newLettersList.map { it.isCorrectLetter },
                correctLetter = newLettersList.first().title
            )
        }
    }

    fun checkUserGuess(letter: String) {
        _uiState.update { uiState ->
            val isCorrectAnswer = letter == uiState.correctLetter
            val index = uiState.letters.indexOfFirst { it == letter }
            val newIsCorrectAnswerList = uiState.isCorrectAnswer.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectAnswer
            if (isCorrectAnswer) {
                playSound("${Constants.LETTER_AUDIO}${letter}")
            } else {
                playSound(ERROR_AUDIO)
            }
            uiState.copy(
                isCorrectAnswer = newIsCorrectAnswerList,
                isUserAnswerCorrect = isCorrectAnswer
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