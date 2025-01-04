package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.RevisionListenAndChooseUseCase
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionListenAndChooseMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionListenAndChooseUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTER_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RevisionListenAndChooseViewModel
@Inject constructor(
    private val listenAndChooseUseCase: RevisionListenAndChooseUseCase,
    private val uiStateMapper: UIStateRevisionListenAndChooseMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionListenAndChooseUIState> =
        MutableStateFlow(RevisionListenAndChooseUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentValue = mediaPlayerInteractor.getIsSoundEnable()
            _uiState.update { state ->
                state.copy(shouldPlayFinishAudio = currentValue)
            }
        }
    }

    fun updateLetters() {
        _uiState.update { state ->
            val allLetters = listenAndChooseUseCase.getLettersForRevisionListenAndChoose()
            val mappedLetters = uiStateMapper.map(allLetters)
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
                updateLetters()
            }
        }
    }

    fun playSound(url: String) {
        if (url == FINISH_AUDIO) {
            _uiState.update { state ->
                state.copy(
                    shouldPlayFinishAudio = false
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
