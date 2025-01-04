package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.TaskFindWordInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateTaskFindWordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskFindWordUIState
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
class TaskFindWordViewModel
@Inject constructor(
    private val findWordInteractor: TaskFindWordInteractor,
    private val prefInteractor: PrefInteractor,
    private val uiStateMapper: UIStateTaskFindWordMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskFindWordUIState> =
        MutableStateFlow(TaskFindWordUIState())
    val uiState = _uiState.asStateFlow()
    private var taskCompleted = false

    fun setSelectedLetter(letter: String) {
        _uiState.update { state ->
            state.copy(selectedLetter = letter)
        }
    }

    fun updateWordsForLetter(letterId: String) {
        viewModelScope.launch {
            val filteredWords = findWordInteractor.getWordsForSecondTask(letterId)
            val mappedWords = uiStateMapper.map(filteredWords)
            _uiState.update { state ->
                state.copy(
                    icons = mappedWords.icons,
                    isFlipped = mappedWords.isFlipped,
                )
            }
            delay(200)
            with(mappedWords) {
                _uiState.update { state ->
                    state.copy(
                        lettersId = lettersId,
                        wordsId = wordsId,
                        titles = titles,
                        isCorrectAnswers = isCorrectAnswers,
                        isCorrectWord = false
                    )
                }
            }
        }
    }

    fun flipCard(wordId: String, letterId: String) {
        _uiState.update { state ->
            if (!state.wordsId.contains(wordId)) return@update state

            val isCorrectWord = letterId == state.selectedLetter
            val correctAnswersCount = if (isCorrectWord) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            taskCompleted = correctAnswersCount == 3
            val index = state.wordsId.indexOfFirst { it == wordId }
            val newIsFlippedList = state.isFlipped.toMutableList()
            newIsFlippedList[index] = !newIsFlippedList[index]
            val newIsCorrectAnswerList = state.isCorrectAnswers.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectWord
            state.copy(
                isFlipped = newIsFlippedList,
                isCorrectAnswers = newIsCorrectAnswerList,
                correctAnswersCount = correctAnswersCount,
                isCorrectWord = isCorrectWord,
            )
        }
        if (uiState.value.isCorrectWord) {
            playSound("$WORDS_AUDIO$wordId")
            processCorrectGuess()
        } else {
            playSound(ERROR_AUDIO)
        }
    }

    private fun processCorrectGuess() {
        viewModelScope.launch {
            if (!taskCompleted) {
                delay(1500)
                updateWordsForLetter(uiState.value.selectedLetter)
            } else {
                showDialog()
                saveTaskProgress()
            }
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
        prefInteractor.taskCompleted(Task.FIND_WORD.stableId, uiState.value.selectedLetter)
    }

    private fun playSound(url: String) {
        mediaPlayerInteractor.playerDestroy()
        mediaPlayerInteractor.initPlayer(context, url)
    }

    override fun onCleared() {
        super.onCleared()
        findWordInteractor.clearPreviousWordsList()
        mediaPlayerInteractor.playerDestroy()
    }
}
