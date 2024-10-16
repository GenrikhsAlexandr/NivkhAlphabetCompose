package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.FourthTaskInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FourthTaskUIState
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
class FourthTaskViewModel
@Inject constructor(
    private val prefInteractor: PrefInteractor,
    private val fourthTaskInteractor: FourthTaskInteractor,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<FourthTaskUIState> =
        MutableStateFlow(FourthTaskUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentValue = mediaPlayerInteractor.getIsSoundEnable()
            _uiState.update { state ->
                state.copy(shouldPlayFinishAudio = currentValue)
            }
        }
    }

    fun setSelectedLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(selectedLetter = letter)
        }
    }

    fun updateWordsForLetter(letterId: String) {
        viewModelScope.launch {
            val filteredWords = fourthTaskInteractor.getWordsForFourthTask(letterId)
            _uiState.update { state ->
                with(filteredWords) {
                    state.copy(
                        wordId = wordId,
                        title = title,
                        icon = icon,
                    )
                }
            }
        }
    }

    fun updateUserGuess(guessedWord: String) {
        _uiState.update { state ->
            state.copy(
                userGuess = guessedWord,
                isGuessWrong = false,
                isClickable = true
            )
        }
    }

    fun deleteLastLetter() {
        val currentUserGuess = uiState.value.userGuess
        if (currentUserGuess.isNotEmpty()) {
            val newUserGuess = currentUserGuess.dropLast(1)
            _uiState.update { state ->
                state.copy(
                    userGuess = newUserGuess
                )
            }
        }
    }

    fun checkUserGuess(word: String) {
        _uiState.update { state ->
            val isAnswerCorrect = word == state.title
            if (isAnswerCorrect) {
                playSound("$WORDS_AUDIO${state.wordId}")
            } else {
                playSound(ERROR_AUDIO)
            }
            val correctAnswersCount = if (isAnswerCorrect) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 3
            state.copy(
                isCompleted = isCompleted,
                correctAnswersCount = correctAnswersCount,
                isGuessWrong = !isAnswerCorrect,
                isClickable = false
            )
        }
        processCorrectGuess()
        saveTaskProgress()
    }

    private fun processCorrectGuess() {
        if (!uiState.value.isGuessWrong && !uiState.value.isCompleted) {
            viewModelScope.launch {
                delay(2000)
                updateUserGuess("")
                updateWordsForLetter(uiState.value.selectedLetter)
            }
        }
    }

    private fun saveTaskProgress() {
        if (uiState.value.isCompleted) {
            prefInteractor.taskCompleted(Task.FOURTH.stableId, uiState.value.selectedLetter)
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
        fourthTaskInteractor.clearPreviousWordsList()
        mediaPlayerInteractor.playerDestroy()
    }
}