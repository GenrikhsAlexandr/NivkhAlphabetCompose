package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.TaskWriteWordInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskWriteWordUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskWriteWordViewModel
@Inject constructor(
    private val prefInteractor: PrefInteractor,
    private val writeWordInteractor: TaskWriteWordInteractor,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskWriteWordUIState> =
        MutableStateFlow(TaskWriteWordUIState())
    val uiState = _uiState.asStateFlow()
    private var taskCompleted = false

    fun setSelectedLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(selectedLetter = letter)
        }
    }

    fun updateWordsForLetter(letterId: String) {
        viewModelScope.launch {
            val filteredWords = writeWordInteractor.getWordsForTaskWriteWord(letterId)
            _uiState.update { state ->
                with(filteredWords) {
                    state.copy(
                        wordId = wordId,
                        title = title,
                        icon = icon,
                        inputWord = ""
                    )
                }
            }
        }
    }

    fun onInputChange(newValue: String) {
        val newText = uiState.value.inputWord + newValue
        _uiState.update { state ->
            state.copy(
                inputWord = newText,
                isGuessWrong = false,
                isClickable = true
            )
        }
    }

    fun deleteLastLetter() {
        val currentUserGuess = uiState.value.inputWord
        if (currentUserGuess.isNotEmpty()) {
            val newText = currentUserGuess.dropLast(1)
            _uiState.update { state ->
                state.copy(
                    inputWord = newText,
                    isGuessWrong = false,
                    isClickable = true
                )
            }
        }
    }

    fun checkUserGuess() {
        val isAnswerCorrect = uiState.value.inputWord == uiState.value.title
        _uiState.update { state ->
            val correctAnswersCount = if (isAnswerCorrect) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            taskCompleted = correctAnswersCount == 3
            state.copy(
                correctAnswersCount = correctAnswersCount,
                isGuessWrong = !isAnswerCorrect,
                isClickable = false
            )
        }
        if (isAnswerCorrect) {
            playSound("$WORDS_AUDIO${uiState.value.wordId}")
            processCorrectGuess()
        } else {
            playSound(ERROR_AUDIO)
        }
    }

    private fun processCorrectGuess() {
        if (!taskCompleted) {
            viewModelScope.launch {
                delay(1500)
                updateWordsForLetter(uiState.value.selectedLetter)
            }
        } else {
            showDialog()
            saveTaskProgress()
        }
    }

    private fun showDialog() {
        viewModelScope.launch {
            delay(1500)
            if (mediaPlayerInteractor.getIsSoundEnable()) {
                playSound(FINISH_AUDIO)
            }
            _uiState.update { state ->
                state.copy(
                    showDialog = true,
                )
            }
        }
    }

    private fun saveTaskProgress() {
        prefInteractor.taskCompleted(Task.WRITE_WORD.stableId, uiState.value.selectedLetter)
    }

    fun playSound(url: String) {
        mediaPlayerInteractor.playerDestroy()
        mediaPlayerInteractor.initPlayer(context, url)
    }

    override fun onCleared() {
        super.onCleared()
        writeWordInteractor.clearPreviousWordsList()
        mediaPlayerInteractor.playerDestroy()
    }
}
