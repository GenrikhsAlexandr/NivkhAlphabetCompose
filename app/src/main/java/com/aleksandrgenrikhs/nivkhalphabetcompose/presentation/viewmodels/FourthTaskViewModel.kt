package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FourthTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
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
@Inject constructor(val interactor: AlphabetInteractor) : ViewModel() {

    private val _uiState: MutableStateFlow<FourthTaskUIState> = MutableStateFlow(
        FourthTaskUIState(
            isNetworkConnected = interactor.isNetWorkConnected()
        )
    )
    val uiState = _uiState.asStateFlow()
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun setLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(selectedLetter = letter)
        }
    }

    suspend fun getWord(letterId: String) {
        isLoading.value = true
        val word = interactor.getWordsForFourthTask(letterId)
        _uiState.update {
            it.copy(
                wordId = word.wordId,
                title = word.title,
                icon = word.icon,
            )
        }
        isLoading.value = false
    }

    fun updateUserGuess(guessedWord: String) {
        _uiState.update { uiState ->
            uiState.copy(
                userGuess = guessedWord,
                isGuessWrong = false,
                isClickable = true
            )
        }
    }

    fun deleteLastLetter() {
        val currentUserGuess = _uiState.value.userGuess
        if (currentUserGuess.isNotEmpty()) {
            val newUserGuess = currentUserGuess.dropLast(1)
            _uiState.update { uiState ->
                uiState.copy(
                    userGuess = newUserGuess
                )
            }
        }
    }

    fun checkUserGuess(word: String) {
        _uiState.update { uiState ->
            val isAnswerCorrect = word == uiState.title
            if (isAnswerCorrect) {
                playSound("${WORDS_AUDIO}${uiState.wordId}")
            } else {
                playSound(ERROR_AUDIO)
            }
            val correctAnswersCount = if (isAnswerCorrect) {
                uiState.correctAnswersCount + 1
            } else {
                uiState.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 3
            if (isAnswerCorrect && !isCompleted) {
                viewModelScope.launch {
                    if (!isLoading.value) {
                        delay(2000)
                        updateUserGuess("")
                        getWord(uiState.selectedLetter)
                    }
                }
            }
            uiState.copy(
                isCompleted = isCompleted,
                correctAnswersCount = correctAnswersCount,
                isGuessWrong = !isAnswerCorrect,
                isClickable = false
            )
        }
        if (uiState.value.isCompleted) {
            interactor.taskCompleted(Task.FOURTH.stableId, uiState.value.selectedLetter)
        }
    }

    fun playSound(url: String) {
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